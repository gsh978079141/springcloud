//package com.gsh.springcloud.user.service;
//
//import org.springframework.amqp.core.Message;
//
///**
//    * @Title: RabbitmqService
//    * @Package com.gsh.clientconfig.service
//    * @Description: Rabbitmq服务接口
//    * @author gsh
//    * @date 2018/9/12 11:10
//    */
//public interface RabbitmqService {
//    void sendFanout(String exchange, String routingKey, Object message);
//    void sendDirect(String queueName, Object message);
//    void sendTopic(String queueName, Object message);
//    Message receive(String key);
//}
