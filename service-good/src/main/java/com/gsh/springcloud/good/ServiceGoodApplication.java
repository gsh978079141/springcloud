package com.gsh.springcloud.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.gsh.springcloud.good.dao")
public class ServiceGoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceGoodApplication.class, args);
	}
}
