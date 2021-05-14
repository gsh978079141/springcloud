node{
    def releaseTag = "${projectVersion == '' ? BUILD_NUMBER:projectVersion}"
    def serviceName = "${projectName}"
    def env = "${projectEnv == '' ? 'dev':projectEnv}"
    def groupName = "dbp-edu"
    def gitRepo = 'http://gerrit.deepblueai.com/EDU_DBP_APP'
    def fetchBranch = 'special_case_520'
    def registryHost = "${env == 'dev' ? 'harbor.':'harbor-cz.'}deepblueai.com"
    def gitCredentialsId = 'gerrit-credential'
    def serviceDirMap = [
        'platform-management' : 'frontend/platform-management',
        'bff-management': 'frontend/bff-management',
        'platform-terminal': 'frontend/platform-terminal',
        'bff-terminal': 'frontend/bff-terminal'
    ]

    stage('Clean workspace') {
        cleanWs()
    }

    stage('Git clone codes') {
        git credentialsId: gitCredentialsId, branch: fetchBranch, url: gitRepo
    }

    stage("Npm build service") {
        String serviceDir = serviceDirMap[serviceName]
        dir("./${serviceDir}") {
            nodejs(nodeJSInstallationName: 'node_12.8.0') {
                withNPM(npmrcConfig:'npm-config') {
                    sh """
                        npm config set registry https://registry.npm.taobao.org
                        npm config set disturl https://npm.taobao.org/dist
                        npm config set cache /root/.npm
                    """
                    sh "npm install && npm run build"
                }
            }
            if (!serviceName.startsWith('bff-')) {
                sh"""
                  cp ../../devops/configs/front-nginx.conf -d ./nginx.conf
                """                    
            }
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

    stage('Clean images on server') {
        sh "docker system prune -f"
    }

    stage('Publish build info') {
        println "It's success to deploy service named ${serviceName}"
    }
}