package com.gsh.springcloud.serverconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ServerConfigApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerConfigApplication.class, args);
	}
}
