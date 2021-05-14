package com.gsh.springcloud.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.gsh.springcloud.user"})
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.gsh.springcloud.user.domain.mapper")
@EnableFeignClients("com.gsh.springcloud.**.client")
@EnableTransactionManagement
@EnableDiscoveryClient
public class ServiceUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceUserApplication.class, args);
  }


}
