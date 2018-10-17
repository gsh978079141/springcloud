package com.gsh.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
/**
    * @Title: OrderService
    * @Package com.gsh.springcloud.service
    * @Description: restTemplate
    * @author gsh
    * @date 2018/9/21 10:27
    */
@Service
public class OrderService {
    @Autowired
    private  RestTemplate restTemplate;

    public   List<String> getOrder(){
        return  restTemplate.getForObject("http://service-member/member/getList.do",List.class);
    }
}
