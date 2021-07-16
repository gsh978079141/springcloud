## 一：基础配置
### 1.上传seata配置至nacos配置中心
    cd ./config-center  -> nacos-config.py/nacos-config.sh
### 2：修改nacos配置中心中mysql相关配置
    主要配置：store.db.url、store.db.user、store.db.password
    nacos中新增配置:
    Data_ID: service.vgroupMapping.service-order_group=dafault
    Group: SEATA_GROUP
    value: default
### 3：启动seata-server
    docker compose up -d 

## 二：项目相关配置
### pom.xml
```xml
   <!--  seata  -->
<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-seata</artifactId>
    </dependency>
    <dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-spring-boot-starter</artifactId>
    </dependency>
</dependencies>

```

```yaml
seata:
  enabled: true
  # Seata 应用名称，默认使用 ${spring.application.name}
  application-id: ${spring.application.name}
  # Seata 事务组, 高版本没找到相关配置, 是否可配置未知 选用默认default
  #  tx-service-group: my_test_tx_group
  tx-service-group: ${spring.application.name}_group
  config:
    type: nacos
    nacos:
      #      命名空间ID
      namespace:
      #      namespace: 'gsh-cloud-uat'
      serverAddr: ${nacos_server_addr:127.0.0.1:8848}
      group: SEATA_GROUP
      userName: ""
      password: ""
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: ${nacos_server_addr:127.0.0.1:8848}
      #      命名空间ID
      namespace:
      userName: ""
      password: ""

# 如不配置此项，则默认group为：service.vgroupMapping.service-user-fescar-service-group
# can not get cluster name in registry config 'service.vgroupMapping.service-user-fescar-service-group'
spring:
  cloud:
    alibaba:
      seata:
        tx-service-group: ${spring.application.name}_group
```