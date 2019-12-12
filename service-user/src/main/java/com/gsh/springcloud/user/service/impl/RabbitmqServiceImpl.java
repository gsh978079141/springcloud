package com.gsh.springcloud.user.service.impl;

import com.gsh.springcloud.user.service.RabbitmqService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.MessageChannelReactiveUtils;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /***
     * @Description  Direct 路由模式
     *              只发送到指定的队列
     * @Date 13:54 2019-04-25
     * @param queueName
     * @param message
     * @return void
     * @Author Gsh
     **/
    @Override
    public void sendDirect(String queueName, Object message ){
        amqpTemplate.convertAndSend(queueName,message);
    }

    /**
    　* @Description: Fanout(订阅模式)
      *  一个生产者，多个消费者，生产者将消息不直接发送到队列，而是发送到了交换机.
      *  每个队列绑定交换机，生产者发送的消息经过交换机，到达队列.
      *  如果将消息发送到一个没有队列绑定的exchange上面，那么该消息将会丢失(rabbitMQ中exchange不具备存储消息的能力，只有队列具备存储消息的能力)
      * 这种模式不需要 RouteKey
    　* @param exchange 交换机 routingKey路由 message 信息
    　* @return void
    　* @throws
    　* @author gsh
    　* @date 2018/9/12 15:28
    　*/
    @Override
    public void sendFanout(String exchange, String routingKey, Object message) {
        ((CachingConnectionFactory) this.rabbitTemplate.getConnectionFactory()).setPublisherConfirms(true);
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }

    @Override
    public void sendTopic(String queueName, Object message) {

    }

    //   @RabbitHandler
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
