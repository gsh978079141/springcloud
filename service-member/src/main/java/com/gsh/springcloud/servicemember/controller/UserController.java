package com.gsh.springcloud.servicemember.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gsh.springcloud.entity.User;
import com.gsh.springcloud.servicemember.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findById")
    public User findById(Integer id){
        System.out.println("UserController   findById   id = "+id);
       return  userService.selectById(id);
    }

    @RequestMapping("/findList")
    public List<User> findList(@RequestBody User user){
        System.out.println("UserController  findList  user =  "+ user);
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(user);
        return userService.selectList(ew);
    }

    @RequestMapping("/getHeader")
    public String getHeader(@RequestHeader String token) throws UnsupportedEncodingException {
        System.out.println("UserController  getHeader  token =  "+ token);
        token = URLDecoder.decode(token,"UTF-8");
        System.out.println(" 解码 decode " + token);
        return token;
    }
}

