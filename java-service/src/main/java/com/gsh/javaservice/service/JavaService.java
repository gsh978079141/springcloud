package com.gsh.javaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class JavaService {
    @Autowired
    private RestTemplate restTemplate;
    public String getPython(){
        return  restTemplate.getForEntity("http://py-sidecar/getUser", String.class).getBody();
    }


}
