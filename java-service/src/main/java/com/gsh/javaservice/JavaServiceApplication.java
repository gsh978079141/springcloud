package com.gsh.javaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JavaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaServiceApplication.class, args);
    }
}
