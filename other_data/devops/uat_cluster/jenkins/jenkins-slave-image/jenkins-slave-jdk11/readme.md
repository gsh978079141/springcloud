**jenkins构建java应用的slave镜像**

基于jenkins/slave:4.7-1-jdk11镜像来构建，
此镜像的操作系统是debian10，预转好jdk11 和jenkins-agent。
还需要安装maven 和 kubectl

~~~~
安装maven，指定本地仓库路径: /opt/maven/repository。
运行时需要将此目录挂载到k8s存储，以避免每次重启pod后maven构建都要从远程仓库重新下载jar。
~~~~


