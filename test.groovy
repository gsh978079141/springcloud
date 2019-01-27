pipeline {
    agent any
    //parameters { string(defaultValue: '', name: 'GIT_TAG', description: '输入-版本号-发布' ) }
    environment {
        def DESTPATH = "/home/dev/${JOB_NAME}"
        def SRCPATH = "."
        def DOCKER_NAME="${JOB_NAME}"
        def WAR_NAME= "ROOT"
        def port = "8089:8080"
    }
    stages {
        stage('代码拉取'){
            steps {
                script{
                    def git_branch = "${GIT_BRANCH}".tokenize('/')[-1]
                    git branch: "${git_branch}",credentialsId: 'e4928c9b-46f7-47ee-96a6-488cf68d4f34', url: 'http://git.cloudyoung.cn/haval/haval-message.git'
                }
            }
        }
        stage('mvn构建') {
            // Run the maven build
            steps {
                //sh "git checkout ${GIT_COMMIT_ID}"
                sh "cd ${SRCPATH};/bin/mvn ${MVN_GOAL}"
            }
        }
        stage('目录检查') {
            steps {
                echo "检查${DESTPATH}目录是否存在"
                script{
                    try{
                        def resultUpdateshell = sh script: 'ansible ${region} -m shell -a "ls -d ${DESTPATH}/webapps"'
                    }
                    catch(e){
                        sh script: 'ansible ${region} -m shell -a "mkdir -p  ${DESTPATH}/{webapps,logs,log}"'
                    }
                }
            }
        }
        stage('服务检查'){
            steps {
                echo "检查docker 进程是否存在"
                script{
                    try{
                        def resultUpdateshell = sh script: 'ansible ${region} -m shell -a "docker ps -a |grep ${DOCKER_NAME}|grep -v grep"'
                        if (resultUpdateshell != 0) {
                            sh 'ansible ${region} -m shell -a "docker rm -f ${DOCKER_NAME}"'
                        }
                    }
                    catch(e){
                        echo "no docker image"
                    }
                }
            }
        }
        // stage('发布确认') {
        //     steps {
        //         input "检查完成，是否发布?"
        //     }
        // }
        stage('代码推送') {
            steps {
                echo "code sync"
                script {
                    try{
                        sh 'mv ${SRCPATH}/target/*.war  ${SRCPATH}/target/${WAR_NAME}.war'
                        def isDir = sh script: 'ansible ${region} -m shell -a "ls -f ${DESTPATH}/webapps/${WAR_NAME}"'
                        //S 测试
                        sh 'vi /etc/ansible/hosts'
                        //E 测试
                        if (resultUpdateshell != 0) {
                            sh 'ansible ${region} -m shell -a "rsync -avz --remove-source-files  ${DESTPATH}/webapps/${WAR_NAME}  /tmp"'
                            sh 'ansible ${region} -m shell -a "rm -fr ${DESTPATH}/webapps/${WAR_NAME}"'
                        }
                    }catch(e){
                        echo "no war"
                    }
                    sh 'ansible ${region} -m synchronize -a "src=${SRCPATH}/target/${WAR_NAME}.war dest=${DESTPATH}/webapps/ rsync_opts=-avz,--delete"'
                }
            }
        }
        stage('启动应用') {
            steps {
                echo "start tomcat"
                script {
                    sh  'ansible  ${region} -m script -a   "/home/dev/sh/docker_tomcat_start.sh ${DESTPATH} ${DOCKER_NAME} ${port} "'
                    sh  'ansible  ${region} -m shell -a   "chown -R dev:dev /home/dev/"'
                }
            }
        }
        stage("端口检查 "){
            steps {
                echo "port check"
                script {
                    def port_result = sh script: 'ansible ${region} -m script -a "/home/dev/sh/check_port.sh ${port} "'
                    echo "${port_result}+--------"
                    if (port_result == 0) {
                        skip = '0'
                        return
                    }
                }
            }
        }
    }
}
