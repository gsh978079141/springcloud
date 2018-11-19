package com.gsh.springcloud.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.gsh.springcloud.member.dao")
public class ServiceMemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMemberApplication.class, args);
	}
}
