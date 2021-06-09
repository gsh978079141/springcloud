## 制作jenkins-slave镜像
jenkins-slave镜像是用来执行构建任务的代理服务的镜像。
* 它需要与jenkins-master通信，所以需要安装 jnlp。
* 需要执行构建任务，所以需要构建依赖的环境：
  java需要对应版本的jdk和maven；
  前端和nodejs应用需要对应的node环境；
* 需要构建docker镜像，所以需要docker环境。
  这里采用通过挂载使用node上的docker的方式，不采用docker in docker的方式。
* 如果需要把镜像发布到k8s，还需要安装k8s-client

~~~~
官方的jenkins-slave镜像, 已经安装了 jnlp 和默认的jdk8。
因为不同的构建任务需要用到的环境不一，需要根据不同的环境构建不同的jenkins-slave镜像。
~~~~

这里制作了三个jenkins-slave镜像，分别用于前后端的应用构建
1. jenkins-slave-jdk8
2. jenkins-slave-jdk11
3. jenkins-slave-node14

前两个镜像用于java应用的构建，分别对应不同的jdk版本，需要安装mavne3，使用公司的maven配置。
第三个用于构建前端或者nodejs应用，需要安装node，这里使用node14。

**构建需要下载kubernetes-client jar包，需要翻墙**