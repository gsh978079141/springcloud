podTemplate(
  containers: [
    containerTemplate(
      name: 'jnlp', 
      image: 'core.harbor.cz.shenlan.com/dbp-edu/jenkins-slave-jdk8:v1', 
      alwaysPullImage: true,
      runAsUser: '0',
      runAsGroup: '0',
      workingDir: '/home/jenkins',
    )
  ], 
  yaml: '''
spec: 
  tolerations:
  - effect: "NoSchedule"
    key: "node-role.kubernetes.io/master"
    operator: "Exists"
''',
  imagePullSecrets: ['harbor-secret'],
  label: 'jenkins-slave-jdk8',
  volumes: [
    persistentVolumeClaim(mountPath: '/home/jenkins', claimName: 'jenkins-pvc-slave-jdk8', readOnly: false),
    hostPathVolume(mountPath:'/usr/bin/docker', hostPath:'/usr/bin/docker'),
    hostPathVolume(mountPath:'/var/run/docker.sock', hostPath:'/var/run/docker.sock'),
    hostPathVolume(mountPath:'/var/lib/docker', hostPath:'/var/lib/docker'),
    hostPathVolume(mountPath:'/opt/var/lib/docker', hostPath:'/opt/var/lib/docker'),
  ]) {

  node('jenkins-slave-jdk8') {
    // 构建标签：生成的docker镜像的标签，不指定时默认用 BUILD_NUMBER 代替
    def buildTag = "${DOCKER_IMAGE_TAG == '' ? BUILD_NUMBER : DOCKER_IMAGE_TAG}"
    // 构建的目标服务分组，可选全部服务构建，全部云端服务构建，全部边缘端服务构建和指定单个服务构建
    def buildTarget = "${BUILD_TARGET}"
  
    def gitRepo = 'ssh://gej@gerrit.deepblueai.com:29418/EDU_DBP_APP'
    def gitCredentialsId = 'gerrit-credential'
    def getBranch = 'master'
  
    def registryHost = 'core.harbor.cz.shenlan.com'
    def registryGroup = 'dbp-edu'
    def registryCredential = 'harbor-credential'
  
    def useSkywalkingAgent = "${USE_SKYWALKING_AGENT}" == 'true'
  
    def serviceDirMap = [
      "bff-student-iot": "cloud/bff-student-iot-parent/bff-student-iot",
      "bff-teacher-iot": "cloud/bff-teacher-iot-parent/bff-teacher-iot",
      "bff-op": "cloud/bff-op",
      "bff-management2": "cloud/bff-management-parent/bff-management",
      "service-exam" : "cloud/service-exam-parent/service-exam",
      "service-foundation" : "cloud/service-foundation-parent/service-foundation",
      "service-dict" : "cloud/service-dict-parent/service-dict",
      "service-account" : "cloud/service-account-parent/service-account",
      "gateway-management": "cloud/gateway-management",
      "gateway-api": "cloud/gateway-api",
      "service-teach": "cloud/service-teach-parent/service-teach",
      "service-job": "cloud/service-job",
      "service-oss": "cloud/service-oss",
      "edge-service-media": "edge/edge-service-media-parent/edge-service-media",
      "edge-service-algorithm-dispatch": "edge/edge-service-algorithm-dispatch-parent/edge-service-algorithm-dispatch",
      "edge-service-file-manager": "edge/edge-service-file-manager-parent/edge-service-file-manager",
      "edge-service-student-iot": "edge/edge-service-student-iot",
      "edge-service-teacher-iot": "edge/edge-service-teacher-iot",
      "edge-service-video-process": "edge/edge-service-video-process-parent/edge-service-video-process",
      "edge-service-biz": "edge/edge-service-biz-parent/edge-service-biz",
      "edge-service-k12": "edge/edge-service-k12-parent/edge-service-k12"
      ]
    def serviceListToDeploy = []
    def edgeServices =[];
    def cloudServices =[]; 
  
    serviceDirMap.each{ svcName, svcDir -> 
      if(svcName.startsWith('edge')) {
        edgeServices.add(svcName)
      } else {
        cloudServices.add(svcName)
      }
    }
  
    if(buildTarget == 'all') {
      serviceListToDeploy = cloudServices.plus(edgeServices)
    } else if(buildTarget == 'all-cloud'){
      serviceListToDeploy = cloudServices
    } else if(buildTarget == 'all-edge'){
      serviceListToDeploy = edgeServices
    } else {
      serviceListToDeploy.add(buildTarget)
    }

    stage('git fetch') {
      checkout([
        $class: 'GitSCM', 
        branches: [[name: getBranch]], 
        doGenerateSubmoduleConfigurations: false, 
        extensions: [
            // [$class: 'CleanCheckout'], // 会删除 .node_modules目录，导致不能使用缓存加速构建，所以去掉此选项
            [$class: 'CloneOption', depth: 1, noTags: false, reference: '', shallow: true, timeout: 15]
        ], 
        submoduleCfg: [], 
        userRemoteConfigs: [[credentialsId: gitCredentialsId, url: gitRepo]]
      ])
      sh 'git clean  -df'
    }
  
    stage('maven build') {
      dir("./backend") {
        sh 'mvn clean install -Dmaven.repo.local=/home/jenkins/mvn_repo/'
      }
    }
  
    stage('docker build & push') {
      println "serviceListToDeploy: ${serviceListToDeploy}"
      serviceListToDeploy.each{ svcName -> 
        println "serviceDirMap contains [${svcName}]: ${serviceDirMap.containsKey(svcName as String)}"
        def svcDir = serviceDirMap.get(svcName as String)
        def mainDir = "./backend/${svcDir}"
        dir(mainDir) {
          def serviceImage;
          if (useSkywalkingAgent){
            serviceImage = docker.build("${registryGroup}/${svcName}-agent:${buildTag}", "-f Dockerfile-agent .")
          } else {
            serviceImage = docker.build("${registryGroup}/${svcName}:${buildTag}")
          }
          docker.withRegistry("https://${registryHost}", registryCredential) {
            serviceImage.push()
            serviceImage.push('latest')
          }
        }
      }
    }
  
    stage('Deploy service') {
      println "Begin deploying services: ${serviceListToDeploy}"
      // 需要跳过的服务，单独部署
      def ignoredServices = ['edge-service-k12']
      serviceListToDeploy.each{ svcName ->
        if(ignoredServices.contains(svcName)) {
          return true
        }
        def namespace = 'edu-cloud-dev'
        def serviceName = svcName
        if (svcName.startsWith('edge')) {
          serviceName = serviceName + '-shangyin-dev'
          namespace = 'edu-edge-dev'
        }
        
        def image = "core.harbor.cz.shenlan.com/${registryGroup}/${svcName}:${buildTag}"
        
        if (useSkywalkingAgent){
            image = "core.harbor.cz.shenlan.com/${registryGroup}/${svcName}-agent:${buildTag}"
        }
        
        sh """
          kubectl set image deployments/${serviceName} ${svcName}=${image} -n ${namespace}
        """
      }
      println "Finish deploying services: ${serviceListToDeploy}"
    }
  
    // stage('Clean images') {
    //     sh 'docker system prune -f'
    // }
  }

}
