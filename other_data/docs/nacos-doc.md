本项目云端服务使用Nacos作为配置中心统一管理配置

## spring cloud 项目集成nacos配置中心

1. 在pom文件中引入依赖jar

````xml
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
````

2. bootstrap.yml配置文件里添加nacos相关配置

````yaml
spring:
  profiles:
    active: dev
  application:
    name: service-iot
  cloud:
    nacos:
      config:
        server-addr: ${nacos_server_addr:192.168.1.10:8848} #nacos服务器地址:端口号
        file-extension: yaml #nacos配置中心文件扩展名
        namespace: edu #命名空间 
````

${spring.application.name} ${spring.profiles.active} 根据服务名-dev/test来区分不同的环境配置（请参考第三节）

# nacos控制台


