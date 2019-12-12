package com.gsh.springcloud.user.controller;

import com.gsh.springcloud.user.model.QueueMessage;
import com.gsh.springcloud.user.service.RabbitmqService;
import com.rabbitmq.client.AMQP;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.core.Message;

@RestController
@RequestMapping("/user")
public class TestController {

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
    @RequestMapping("/sendFanout")
    public void send(String exchange, String routingKey, @RequestBody User message){
        System.out.println("gsh  sending " + message);
        rabbitmqService.sendFanout(exchange, routingKey, message);
    }
    /**
     * 信息发送
     * @param message 信息体
     */
    @RequestMapping("/send2")
    public void send2(@RequestBody QueueMessage<User> message){
        System.out.println("gsh  sending " + message);
        rabbitmqService.sendFanout(message.getExchange(), message.getRoutingKey(), message.getMessage());
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
