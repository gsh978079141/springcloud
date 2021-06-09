jenkins 部署配置
## 节点规划： 
master1， master2， master3 此三个节点用于jenkins任务调度节点
## 存储规划：
使用local-pv，挂载三个节点目录/opt/jenkins_volume/


### 安装步骤
使用helm安装jenkins, 需要预先安装helm.  
[Install Helm](https://helm.sh/docs/intro/install/)

添加 jenkinsci 仓库

````bash
helm repo add jenkinsci https://charts.jenkins.io
helm repo update
````

1. 创建namespace

````bash
kubectl create namespace jenkins
# 创建 docker-registry secret: harbor-secret
kubectl create secret docker-registry harbor-secret --docker-server=core.harbor.cz.shenlan.com --docker-username=dbp-edu --docker-password=D8jWTYOI2q -n jenkins
````

2. 创建serviceAccount

````bash
kubectl apply -f jenkins-sa.yaml
````
3. 创建jenkins存储
存储规划：
jenkins-master 运行在master1上，存储目录/opt/jenkins_volume

jenkins-slave-jdk8,jenkins-slave-jdk11 运行在master2上， 这两个执行java的构建任务，
存储目录/opt/jenkins_slave_jdk8，/opt/jenkins_slave_jdk11。

jenkins_slave_node14 运行在master3上，执行前端构建任务，存储目录/opt/jenkins_slave_node14


````bash
kubectl apply -f jenkins-storage.yaml
````

3. 使用helm命令安装

````bash
chart=jenkinsci/jenkins
helm install jenkins -n jenkins -f jenkins-values.yaml $chart
````



