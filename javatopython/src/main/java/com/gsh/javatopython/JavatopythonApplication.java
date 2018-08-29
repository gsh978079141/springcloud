package com.gsh.javatopython;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@EnableSidecar
@EnableEurekaClient
@SpringBootApplication
public class JavatopythonApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavatopythonApplication.class, args);
    }
}
