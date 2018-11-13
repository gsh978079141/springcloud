package com.gsh.springcloud.servicegood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceMemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMemberApplication.class, args);
	}
}
