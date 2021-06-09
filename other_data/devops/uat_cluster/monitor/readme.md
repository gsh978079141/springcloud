# 搭建整个微服务系统监控体系，提高系统的可观测性（Observability）
监控主要分为三个维度：
* metrics
* logging
* tracing



#  使用Elastic Cloud on Kubernetes (ECK) 安装elk集群
[ECK官方文档](https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-quickstart.html)   
Elastic Cloud on Kubernetes简称ECK，其扩展了Kubernetes的基本编排功能，以支持Kubernetes上Elasticsearch，Kibana和APM Server的设置和管理。  
借助ECK可以简化所有关键操作：
* 管理监控多个集群
* 扩展缩小集群
* 变更集群配置
* 调度备份
* 使用TLS证书保护集群
* 采用区域感知实现hot-warm-cold架构

## 1. 安装 ECK

首先在集群中安装 ECK 对应的 Operator 资源对象：

````
kubectl apply -f https://download.elastic.co/downloads/eck/1.5.0/all-in-one.yaml
````

安装成功后，会自动创建一个 elastic-system 的 namespace 以及一个 operator 的 Pod：
````
kubectl get pods -n elastic-system
````

## 3. 部署ES

这里我们搭建一个3节点的集群，节点名称 node1, node2, node3，使用本地存储卷做存储。每个节点都承担master和data的角色。
配置es存储：   
````
kubectl apply -f elastic-storage.yaml
````
部署es应用：   
````
kubectl apply -f elastic.yaml
````
使用ingress对外暴露es：https://edu-elastic.deepblueai.com
````
kubectl apply -f elastic-ingress.yaml
````

## 4. 部署kibana
### 部署kibana应用： 
````
kubectl apply -f kibana.yaml
````
### 使用ingress对外开放[kibana](https://edu-elastic.deepblueai.com)
````
kubectl apply -f kibana-ingress.yaml
````

## 5. 部署Logstash
Logstash并未集成在ECK中，我们需要采用Helm方式部署。
使用helm安装Logstash, 需要预先安装helm.  
[Install Helm](https://helm.sh/docs/intro/install/)

````bash
helm repo add elastic https://helm.elastic.co
helm repo update
````

###  创建logstash存储
存储规划：使用node4上/opt/logstash-volume目录，容量设置30G
部署logstash存储
````bash
kubectl apply -f logstash-storage.yaml
````
### 部署logstash应用
````bash
helm install logstash -n monitor -f logstash-values.yaml elastic/logstash
````
### 升级logstash应用，更新了values
````bash
helm upgrade logstash -n monitor -f logstash-values.yaml elastic/logstash
````

## 6. 部署cerebro
Cerebro是使用Scala、Play Framework、AngularJS和Bootstrap构建的开源的基于Elasticsearch Web可视化管理工具。可以通过Cerebro对集群进行web可视化管理，如执行rest请求、修改Elasticsearch配置、监控实时的磁盘，集群负载，内存使用率等。

````bash
helm repo add stakater https://stakater.github.io/stakater-charts
helm repo update
helm install cerebro -n monitor -f cerebro-values.yaml stakater/cerebro
````

## 7. 部署skywalking

### 添加skywalking repo
当前目录下skywalking目录(chart),来自github的 https://github.com/apache/skywalking-kubernetes 的skywalking-kubernetes/chart/skywalking
````
# 需要先安装elastic repo，之前已安装过
# helm repo add elastic https://helm.elastic.co
helm dep up skywalking
````

### 使用helm3部署
````
helm install skywalking -n monitor -f skywalking-values.yaml skywalking
````

### 升级或更新skywalking应用
````bash
helm upgrade skywalking -n monitor -f skywalking-values.yaml skywalking
````
### 卸载skywalking应用
````bash
helm uninstall skywalking -n monitor
````