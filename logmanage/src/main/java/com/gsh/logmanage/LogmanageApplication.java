package com.gsh.logmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LogmanageApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogmanageApplication.class, args);
	}
}
