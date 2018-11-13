package com.gsh.springcloud.serviceorder.controller;

import com.gsh.springcloud.serviceorder.service.OrderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRestService orderRestService;
    @RequestMapping("/getMemberList.do")
    public List<String> getOrder(){
        System.out.println("订单服务调用会员服务  开始");
        return  orderRestService.getOrder();
    }
}
