node{
    def releaseTag = "${projectVersion == '' ? BUILD_NUMBER:projectVersion}"
    def serviceName = "${projectName}"
    def env = "${projectEnv == '' ? 'dev':projectEnv}"
    def groupName = "dbp-edu"
    def registryHost = "${env == 'dev' ? 'core.harbor.cz.shenlan.com':'harbor-cz.deepblueai.com'}"
    def gitRepo = 'http://gerrit.deepblueai.com/EDU_DBP_APP'
    def gitCredentialsId = 'gerrit-credential'
    def fetchBranch = 'special_case_520'
    def serviceDirMap = [
        'service-exam' : 'services/service-exam',
        'service-foundation' : 'services/service-foundation-parent',
        'service-dict' : 'services/service-dict-parent',
        'service-account' : 'services/service-account-parent',
        'server-gateway': 'servers/server-gateway',
        'server-config': 'servers/server-config',
        'service-teach': 'services/service-teach',
        'service-job': 'services/service-job'

    ]

    // stage('Clean workspace') {
    //     cleanWs()
    // }

    stage('Git clone codes') {
        git credentialsId: gitCredentialsId, branch: fetchBranch, url: gitRepo
    }

    stage("Maven build service") {
        String serviceDir = serviceDirMap[serviceName]
        println('whether is a gather service::::' + !serviceDir.endsWith('-parent'))
        dir("./backend/${serviceDir}") {
            withMaven(maven: 'maven_3.5.2') {
                if (serviceDir.endsWith('-parent')) {
                    sh """
                        mvn clean deploy -Dmaven.test.skip=true -Ddockerfile.skip
                    """
                } else {
                    sh """
                        mvn clean install -Dmaven.test.skip=true -Ddockerfile.skip
                    """
                }

            }
        }
    }

    stage('Build&push images') {
        String serviceDir = serviceDirMap[serviceName]
        String mainDir = "./backend/${serviceDir}"
        if (serviceDir.endsWith('-parent')) {
            mainDir = mainDir.plus("/${serviceName}")
        }
        dir("${mainDir}"){
            def serviceImage = docker.build("${groupName}/${serviceName}:${releaseTag}")
            docker.withRegistry("https://${registryHost}", 'harbor-credential') {
                serviceImage.push()
                serviceImage.push('latest')
            }
        }

    }

    stage('Deploy application') {
        withKubeConfig([credentialsId: 'kubernetes-token-lab', serverUrl: 'https://10.16.33.117:6443']) {
          sh """
            kubectl set image deploy/${serviceName}-${env} ${serviceName}=${registryHost}/${groupName}/${serviceName}:${releaseTag} -n ${env}-edu
            kubectl rollout status deploy/${serviceName}-${env} -n ${env}-edu
            kubectl delete pod \$(kubectl get pod -n ${env}-edu|grep 'Terminat'|awk '{print \$1}') -n ${env}-edu --force --grace-period=0
          """
        }
    }

    stage('Publish build info') {
        println "It's success to deploy service named ${serviceName}"
    }

    stage('Clean images') {
        sh "docker system prune -f"
    }
}