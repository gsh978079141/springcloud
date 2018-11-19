package com.gsh.springcloud.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/member")
public class TestController {
    @RequestMapping("/getList.do")
    public List<String> getList(){
        List<String> list =new ArrayList<>();
        list.add("zhangsan ");
        list.add("lisi");
        list.add("wangwu");
        list.add("service-member 01");
        return list;
    }


}
