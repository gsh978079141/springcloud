package com.gsh.springcloud.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gsh
 */

@SpringBootApplication(scanBasePackages = "com.gsh.springcloud")
public class GatewayApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(GatewayApiApplication.class, args);
  }
}
