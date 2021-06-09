podTemplate(
  containers: [
    containerTemplate(
      name: 'jnlp', 
      image: 'core.harbor.cz.shenlan.com/dbp-edu/jenkins-slave-node14:v1', 
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
  label: 'jenkins-slave-node14',
  volumes: [
    persistentVolumeClaim(mountPath: '/home/jenkins', claimName: 'jenkins-pvc-slave-node14', readOnly: false),
    hostPathVolume(mountPath:'/usr/bin/docker', hostPath:'/usr/bin/docker'),
    hostPathVolume(mountPath:'/var/run/docker.sock', hostPath:'/var/run/docker.sock'),
    hostPathVolume(mountPath:'/var/lib/docker', hostPath:'/var/lib/docker'),
    hostPathVolume(mountPath:'/opt/var/lib/docker', hostPath:'/opt/var/lib/docker'),
  ]) {

  node('jenkins-slave-node14') { 
    def buildTag = "${DOCKER_IMAGE_TAG == '' ? BUILD_NUMBER : DOCKER_IMAGE_TAG}"
    // 构建的目标服务分组，可选全部服务构建，全部云端服务构建，全部边缘端服务构建和指定单个服务构建
    def serviceName = "${BUILD_TARGET}"

    def gitRepo = 'ssh://gej@gerrit.deepblueai.com:29418/EDU_DBP_APP'
    def gitCredentialsId = 'gerrit-credential'
    def getBranch = 'master'
  
    def registryHost = 'core.harbor.cz.shenlan.com'
    def registryGroup = 'dbp-edu'
    def registryCredential = 'harbor-credential'

    def serviceDirMap = [
      'bff-management' : 'frontend/bff-management',
      'platform-management' : 'frontend/platform-management',
      'video-capture': 'internal-tools/video-capture'
    ]

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
      sh "git clean  -df"
    }

    stage("yarn build") {
      if (serviceName.startsWith('bff-')) {
          println "build ${serviceName}, skip npm build!"
          return;
      }
      def serviceDir = serviceDirMap[serviceName]
      dir("./${serviceDir}") {
        sh "yarn install"
        sh "yarn run build"
      }
    }

    stage('docker build') {
      def serviceDir = serviceDirMap[serviceName]
      dir("./${serviceDir}") {
        def serviceImage = docker.build("${registryGroup}/${serviceName}:${buildTag}")
        docker.withRegistry("https://${registryHost}", registryCredential) {
          serviceImage.push()
          serviceImage.push('latest')
        }
        sh "docker system prune -f"
      }
    }
  
    stage('deploy to dev') {
      sh """
          kubectl set image deployments/${serviceName} ${serviceName}=core.harbor.cz.shenlan.com/${registryGroup}/${serviceName}:${buildTag} -n edu-cloud-dev
        """
      println "It's success to deploy service named ${serviceName}"
    }
  }
}
