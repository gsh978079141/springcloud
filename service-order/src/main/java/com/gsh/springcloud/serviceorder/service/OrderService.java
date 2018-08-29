package com.gsh.springcloud.serviceorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private  RestTemplate restTemplate;

    public   List<String> getOrder(){
        return  restTemplate.getForObject("http://service-member/member/getList.do",List.class);
    }
}
