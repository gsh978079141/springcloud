package com.gsh.javaservice.ConfigBean;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
    * @Title: RestTemplateConfig
    * @Package com.gsh.springcloud.serviceorder.ConfigBean
    * @Description: 一定要@loadBalance注解修饰的restTemplate才能实现服务名的调用，
    * 没有修饰的restTemplate是没有该功能的。@loadBalance是Netflix的ribbon中的一个负载均衡的注解
    * @author gsh
    * @date 2018/7/20 13:10
    */
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
}
