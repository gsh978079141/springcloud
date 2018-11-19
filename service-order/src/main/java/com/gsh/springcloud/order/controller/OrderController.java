package com.gsh.springcloud.order.controller;

import com.gsh.springcloud.order.service.OrderService;
import com.gsh.springcloud.order.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    LocalService localService;

    /**
     　* @Description: lcn测试
     　* @param order  user
     　* @return
     　* @throws
     　* @author gsh
     　* @date 2018/11/14 14:08
     　*/
    @RequestMapping("/lcnLocal")
    public String lcnLocal(){
        System.out.println(" lcnLocal start");
        localService.lcn();
        System.out.println(" lcnLocal end");
        return "lcnLocal";
    }

    @RequestMapping("/lcnMp")
    public String lcnMp(){
        System.out.println(" lcnMp start");
        orderService.lcn();
        System.out.println(" lcnMp end");
        return "lcnMp";
    }


}

