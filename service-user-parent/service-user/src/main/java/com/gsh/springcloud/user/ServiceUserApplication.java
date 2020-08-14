package com.gsh.springcloud.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("com.gsh.springcloud.user.dao")
@EnableFeignClients("com.gsh.springcloud.*.client")
@EnableTransactionManagement
public class ServiceUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceUserApplication.class, args);
  }


}
