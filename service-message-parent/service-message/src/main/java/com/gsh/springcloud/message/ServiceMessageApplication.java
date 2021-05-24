package com.gsh.springcloud.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.gsh.springcloud.message"})
@EnableFeignClients("com.gsh.springcloud.*.client")
@EnableDiscoveryClient
public class ServiceMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMessageApplication.class, args);
    }

}
