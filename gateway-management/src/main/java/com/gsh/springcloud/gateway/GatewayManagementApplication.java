package com.gsh.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.gsh.springcloud")
public class GatewayManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayManagementApplication.class, args);
  }

}
