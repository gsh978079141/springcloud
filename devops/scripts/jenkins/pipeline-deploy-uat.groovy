node {
    def releaseTag = "${projectVersion == '' ? BUILD_NUMBER : projectVersion}"
    def serviceName = "${projectName}"
    def groupName = "dbp-edu"
    def registryHost = 'harbor-cz.deepblueai.com'

    stage('Clean workspace') {
         cleanWs()
    }

    stage('Get image') {
        println "docker image is::::${serviceName} with tag::::${releaseTag}"
    }

    stage('Deploy application') {
        withKubeConfig([credentialsId: 'kubernetes-token-lab', serverUrl: 'https://10.16.33.117:6443']) {
            sh """
                kubectl set image deploy/${serviceName}-uat ${serviceName}=${registryHost}/${groupName}/${serviceName}:${releaseTag} -n uat-edu
                kubectl rollout status deploy/${serviceName}-uat -n uat-edu
                kubectl delete pod \$(kubectl get pod -n uat-edu|grep 'Terminat'|awk '{print \$1}') -n uat-edu --force --grace-period=0
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