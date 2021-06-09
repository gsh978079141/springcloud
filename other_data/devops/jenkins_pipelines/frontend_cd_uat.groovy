// 定义参数

pipeline {

  parameters {
    choice(
      name: 'SERVICE', 
      choices: [
        'platform-management',
        'bff-management',
      ], 
      description: '选择需要部署的服务？'
    )
    string(name: 'DOCKER_IMAGE_TAG', defaultValue: 'latest', description: '选择要部署的镜像版本？')
  }

  agent {
    kubernetes {
      label 'jenkins-slave-jdk8'
      defaultContainer 'jnlp'
      yamlFile 'devops/jenkins_pipelines/pod_templates/jnlp_pod.yaml'
    }
  }

  stages {
    stage("check image") {
      steps {
        script {
          sh """ 
            curl -X GET "https://core.harbor.cz.shenlan.com/api/repositories/dbp-edu%2${params.SERVICE}/tags/${params.DOCKER_IMAGE_TAG}/manifest" -H "accept: application/json"
            """
        }
      }
    }

    stage("deploy") {
      steps {
        script {
          def serviceName = "${params.SERVICE}"
          def buildTag = "${params.DOCKER_IMAGE_TAG}"
          sh """
          kubectl set image deployments/${serviceName} ${serviceName}=core.harbor.cz.shenlan.com/dbp-edu/${serviceName}:${buildTag} -n edu-cloud
          """
          println "It's success to deploy service named ${serviceName}"
        }
      }
    }
  }

}



