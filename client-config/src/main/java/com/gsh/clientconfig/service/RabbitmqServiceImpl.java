package com.gsh.clientconfig.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
    * @Title: RabbitmqServiceImpl
    * @Package com.gsh.clientconfig.service
    * @Description:  Rabbitmq服务实现类
    * @author gsh
    * @date 2018/9/12 11:14
    */
@Service

public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
    　* @Description:
    　* @param exchange 交换机 routingKey路由 message 信息
    　* @return void
    　* @throws
    　* @author gsh
    　* @date 2018/9/12 15:28
    　*/
    @Override
    public void send(String exchange, String routingKey, Object message) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
//    @RabbitHandler
// 8 @RabbitListener(queues = "queues1", containerFactory = "rabbitListenerContainerFactory")
// 9 public void process(@Payload String orderXML) {
//        10
//        11     //处理内容
//        12 }
    @RabbitHandler
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory")
    @Override
    public Message receive(String key) {
        return  amqpTemplate.receive(key);
    }
}
