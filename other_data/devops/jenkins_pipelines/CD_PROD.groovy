// 定义参数

pipeline {

  environment { 
    CC = 'clang'
  }

  parameters {
    choice(name: 'ENV', 
      choices: [
        'uat',
        'prod',
      ], 
      description: 'Select the enviroment you want to deploy.'
    )
    string(name: 'DOCKER_IMAGE_TAG', defaultValue: '', description: 'Enter the tag of docker image.')
    booleanParam(name: 'USE_SKYWALKING_AGENT', defaultValue: false, description: 'build image based skywalking-agent-iamge?')
  }

  agent { 
    kubernetes {
      label 'jenkins-worker'
      defaultContainer 'jnlp'
      yamlFile 'manifests/KubernetesPod.yaml'
    }
   }


  stages {
    stage("deploy") {
      steps {
        script {
          
        }
      }
    }
  }

}



