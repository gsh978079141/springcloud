package com.gsh.clientconfig.service;

import org.springframework.amqp.core.Message;

/**
    * @Title: RabbitmqService
    * @Package com.gsh.clientconfig.service
    * @Description: Rabbitmq服务接口
    * @author gsh
    * @date 2018/9/12 11:10
    */
public interface RabbitmqService {
    void send(String exchange, String routingKey, Object message);
    Message receive(String key);
}
