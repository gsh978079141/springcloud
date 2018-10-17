package com.gsh.springcloud.controller;


import com.gsh.springcloud.entity.User;
import com.gsh.springcloud.service.OrderFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
/**
    * @Title: FeginController
    * @Package com.gsh.springcloud.controller
    * @Description: fegin描述
    * @author gsh
    * @date 2018/9/21 10:25
    */
@RestController
@RequestMapping("/fegin")
public class FeginController {

    @Autowired
    OrderFeginClient orderFeginClient;

    @RequestMapping("/getAll")
    public List<String> getAll(){
        return  orderFeginClient.getInfo();
    }

    @RequestMapping("/findById")
    public User findById(Integer id){
        System.out.println("FeginController  findById   id = " + id);
        return  orderFeginClient.findById(id);
    }

    @RequestMapping("/findList")
    public List<User> findList(User user){
        System.out.println("FeginController  findList  user = " + user);
        return  orderFeginClient.findList(user);
    }

    @RequestMapping("/getHeader")
    public String getHeader() throws UnsupportedEncodingException {
        String token = URLEncoder.encode("管","UTF-8");
        System.out.println("加密  encode  " + token);
        return orderFeginClient.getHeader(token);
    }
}
