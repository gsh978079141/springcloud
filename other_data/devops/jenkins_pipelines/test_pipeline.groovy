// 定义参数


pipeline {

  environment { 
    CC = 'clang'
  }

  parameters {
    choice(name: 'BUILD_TARGET', 
      choices: [
        'all',
        'all-cloud',
        'all-edge',
        'bff-op',
        'bff-management2',
        'bff-student-iot',
        'bff-teacher-iot',
        'gateway-management',
        'gateway-api',
        'service-account',
        'service-dict',
        'service-exam',
        'service-foundation',
        'service-teach',
        'service-job',
        'service-oss',
        'edge-service-media',
        'edge-service-algorithm-dispatch',
        'edge-service-file-manager',
        'edge-service-student-iot',
        'edge-service-teacher-iot',
        'edge-service-biz',
        'edge-service-video-process',
        'edge-service-k12'
      ], 
      description: 'Select the services you want to build.'
    )
    string(name: 'DOCKER_IMAGE_TAG', defaultValue: '', description: 'Enter the tag of docker image.')
    booleanParam(name: 'USE_SKYWALKING_AGENT', defaultValue: false, description: 'build image based skywalking-agent-iamge?')
  }

  agent any

  stages {

    stage("stage1") {
      steps {
        script {
          println "hello world!"
        }
      }
    }

  }

}



