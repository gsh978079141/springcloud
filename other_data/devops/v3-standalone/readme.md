# 单机部署版本
所有软件应用部署在一台机器，不分云边
## 服务器资源
* System: Centos7.6
* CPU: 32C
* Mem: 64G
* Disk: 系统盘40G；数据盘1T，挂载到/data目录
* Docker: 19.03.12
* docker-compose: 1.29.1


### 硬件调试
* 所有实验台接入内网，配置好ntp服务
* 所有摄像头连接能正常检测到, 所有IP固定为静态IP，能够正常获取视频，配置好ntp服务


## 资源规划
应用的数据存储都在/data目录下, docker存储从/var/lib/docker 改到 /data/dockerlib

```bash
# mysql数据目录
mkdir -p /data/mysql
# nacos日志目录
mkdir -p /data/nacos/logs
# postgresql日志目录
mkdir -p /data/postgresql
# rabbitmq数据没目录
mkdir -p /data/rabbitmq/data
mkdir -p /data/rabbitmq/log
# reids数据目录
mkdir -p /data/redis/data
mkdir -p /data/redis/log

# nginx日志目录
mkdir -p /data/nginx/log
# 文件目录
mkdir -p /data/evidence
mkdir -p /data/files
mkdir -p /data/videos
mkdir -p /data/uploadcache
mkdir -p /data/videoout
mkdir -p /data/live_videos

# app日志目录
mkdir -p /data/app/log

# es 存储目录
mkdir -p /data/elasticsearch

# prometheus, grafana 数据目录
mkdir -p /data/prometheus
mkdir -p /data/grafana
```


|应用| 存储规划 | 资源上限 | 网络模式 | 开放端口号 | 
| ---- | :--- | :---: | :---: |:---: |
| mysql | /data/mysql | 2C 4G | edu-network | 3306  |
| naccos | /data/nacos/logs | 1C 2G | edu-network | 8848，9848，9555 |
| keycloak | /data/nacos/logs | 1C 2G | edu-network | 8080 |
| postgres | /data/postgres | 1C 2G | edu-network | 5432 |
| rabbitmq | /data/rabbitmq | 1C 2G | edu-network | 5672, 15672 |
| nginx | /data/nginx/log | 4C 4G | edu-network | 9203 |
| emqx |  | 1C 2G | edu-network | 18083,1883,8083,8084 |
| kms | /data/videos | 8C 16G | host | 8888 |
| redis | /data/redis | 2C 2G | edu-network | 6379 |
| service-account |  | 2C 2G | edu-network | 6001 |
| service-dict |  | 2C 2G | edu-network | 6002 |
| service-oss |  | 2C 2G | edu-network | 6004 |
| service-foundation |  | 2C 2G | edu-network | 7001 |
| service-exam |  | 2C 2G | edu-network | 7003 |
| service-teach |  | 2C 2G | edu-network | 7004 |
| bff-student-iot |  | 2C 2G | edu-network | 7101 |
| bff-teacher-iot |  | 2C 2G | edu-network | 7102 |
| bff-management |  | 2C 2G | edu-network | 7103 |
| bff-op |  | 2C 2G | edu-network | 7104 |
| platform-management |  | 2C 2G | edu-network | 8000 |
| gateway-management |  | 2C 2G | edu-network | 8002 |
| edge-service-student-iot |  | 2C 2G | edu-network | 9001 |
| edge-service-teacher-iot |  | 2C 2G | edu-network | 9002 |
| edge-service-file-manager |  | 2C 2G | edu-network | 9003 |
| edge-service-video-process |  | 8C 16G | edu-network | 9004 |
| edge-service-biz |  | 2C 2G | edu-network | 9005 |
| edge-service-media |  | 2C 2G | edu-network | 9006 |
| edge-service-algorithm-dispatch |  | 2C 2G | edu-network | 9101 |
| elasticsearch |  | 2C 4G | edu-network | 9200,9300 |
| kibana |  | 1C 1G | edu-network | 5601 |
| cerebro |  | 1C 1G | edu-network | 9000 |
| prometheus |  | 1C 2G | edu-network | 9090 |
| node-exporter |  | 1C 1G | edu-network | 9100 |
| grafana |  | 1C 2G | edu-network | 3000 |
| weave-scope |  |  | host | 4040 |
| skywalking-oap | 1C 2G |  | edu-network | 1234,11800,12800 |
| skywalking-ui | 0.5C 1G |  | edu-network | 8081 |



## 网络规划


## 系统部署

### 部署mysql 5.7
使用方有 nacos，云端服务，边缘端服务  
初始化数据库文件包括：
* nacos-mysql-2.0.0.sql
* service-dict-init.sql
* service-foundation-init.sql
* service-exam-init.sql
* service-teach-init.sql

### 部署redis

### 部署nacos
**TODO  导入NACOS配置**

### 部署keycloak及其依赖的postgresql

### 部署rabbitmq

### 部署emqx


### 部署kms
**TODO   心跳检测**

### 部署应用
* service-dict
* service-account
* service-foundation
* service-exam
* service-teach
* service-oss(TODO)
* service-job
* bff-management
* gateway-management
* platform-management




## 数据迁移

### 1. keycloak数据初始化
* 创建 realm 和 client等相关数据
具体操作见[confluence](https://confluence.deepblueai.com/pages/viewpage.action?pageId=53937794)

* 导入权限数据，从已有环境里导出json数据,在新环境里导入，具体操作：
调用旧系统service-account服务的 /extra/export_client_authorization接口，指定要导出的realm，client和角色以获取json数据, 例如：
```json
{
	"clientId": "edu-management-bff",
	"realmName": "uat",
	"targetRoles": ["管理员"]
}
```
保存返回json，data字段的数据保存，更改realm为想要导入的新系统的realm，调用/extra/import_client_authorization接口导入。

### 2. 应用数据初始化
1. 迁移数据
数据初始化文件service-foundation-init.sql 在mysql首次启动时执行， 无需重新导入

* 创建学校数据，手动执行sql
````sql
--  创建学校数据
INSERT INTO `edu_foundation`.`school` (`id`, `name`, `area_code`, `property`, `stage`, `contact_number_prefix`, `contact_number`, `address`) VALUES ('1', '深兰实验初中', '021', '公办', '初中', '021', '888888', '上海市');
````
* 创建学校管理员账号，调用bff-management接口
生产环境搭建好后，bff-management 应该对外关闭访问，只能从应用网关->bff->service-foundation 。
现在数据导入阶段可以放开限制。

```bash
curl --location --request POST 'http://192.168.40.244:7103/teaching-staffs' \
--header 'Content-Type: application/json' \
--data-raw '{
    "schoolId": 1,
	"identityNumber": "320001198002012342",
	"classTeacher": false,
	"mobilePhone": "13000000000",
	"name": "管理员",
	"position": "0055_002",
	"remark": "学校管理员",
	"roleNames": ["管理员"],
	"sex": true,
	"stage": "0012_002"
}'
```


需要EXCEL导入的数据：
* 班级数据 excel导入
[样例数据]()
学生数据

AI算法实验数据
实验室数据（包括其下的实验台和摄像头数据）
