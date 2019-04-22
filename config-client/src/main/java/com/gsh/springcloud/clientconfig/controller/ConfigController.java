package com.gsh.springcloud.clientconfig.controller;

import com.gsh.springcloud.clientconfig.service.RabbitmqService;
import entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/config-client")
public class ConfigController {

    @Autowired
    RabbitmqService rabbitmqService;
    @Value("${spring.application.name}")
    private String name;

    @RequestMapping("/from")
    public String from() {
        return this.name;
    }

    /**
     * 信息发送
     * @param exchange 交换机
     * @param routingKey  路由
     * @param message 信息体
     */
    @RequestMapping("/send")
    public void send(String exchange, String routingKey, @RequestBody User message){
        System.out.println("gsh  sending " + message);
        rabbitmqService.send(exchange, routingKey, message);
    }

    /**
     *信息接收
     * @param key
     * @return
     */
    @RequestMapping("/receive")
    public String receive(String key){
        System.out.println("receive**********");
        Message message = rabbitmqService.receive(key);
        System.out.println(message.toString());
        return  message.getBody().toString();
    }
}
