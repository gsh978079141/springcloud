package com.gsh.springcloud.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//多数据源打开注释
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("com.gsh.springcloud.order.dao")
@EnableFeignClients("com.gsh.springcloud.*.client")
@EnableTransactionManagement
//@EnableElasticsearchRepositories("com.gsh.springcloud.order.service.es")
public class ServiceOrderApplication {
  public static void main(String[] args) {
    //解决netty冲突
    System.setProperty("es.set.netty.runtime.available.processors", "false");
    SpringApplication.run(ServiceOrderApplication.class, args);
  }
}


