// 定义参数

pipeline {

  environment { 
    CC = 'clang'
  }

  parameters {
    choice(name: 'ENV', 
      choices: [
        'all',
        'platform-management',
        "gateway-management",
        "gateway-api"
        'bff-management',
        "bff-management2"
        "bff-student-iot",
        "bff-teacher-iot",
        "bff-op",
        "service-foundation",
        "service-exam",
        "service-teach"
        "service-dict",
        "service-account",
        "service-job",
        "service-oss",
        "edge-service-student-iot",
        "edge-service-teacher-iot",
        "edge-service-biz",
        "edge-service-media",
        "edge-service-algorithm-dispatch",
        "edge-service-file-manager",
        "edge-service-video-process",
      ], 
      description: '选择需要部署的服务？'
    )
    string(name: 'DOCKER_IMAGE_TAG', defaultValue: '', description: '选择要部署的镜像版本？')
    booleanParam(name: 'USE_SKYWALKING_AGENT', defaultValue: false, description: '镜像是否基于skywalking-agent?')
  }

  agent {
    kubernetes {
      label 'jenkins-slave-jdk8'
      defaultContainer 'jnlp'
      yamlFile 'devops/jenkins_pipelines/pod_templates/jnlp_pod.yaml'
    }
  }

  stages {
    stage("deploy") {
      steps {
        script {
          sh "mvn --version"
        }
      }
    }
  }

}



