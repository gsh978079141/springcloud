package com.gsh.springcloud.message.service;

import entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

import static com.gsh.springcloud.message.model.Queues.REGISTER_ISSUE_COUPONS;


/**
 * @author gsh
 * @Title: Receiver
 * @Package com.gsh.clientconfig.controller
 * @Description: 消息消费者Receiver 使用@RabbitListener注解定义该类对hello队列的监听, 并用@RabbitHandler 注解来指定对消息的处理方法
 * @date 2018/9/12 13:24
 */
@Component
public class Receiver {


    /**
     * 监听队列：qu-test
     * 交换机：ex-test
     * rountkey test.#
     * 监听队列接收信息（ @PayLoad 在知道消息体类型时使用）
     * 签收模式是manual(手工签收)，参数Channel
     * durable 是否持久化
     * test.*(匹配一个点test.abc)，test.#（匹配后面所有test.abc.abc）
     *
     * @param user
     * @RabbitListener 自动初始化rabbitmq队列、交换机等信息
     * @RabbitHandler 收到信息后调用方法
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "qu-test", durable = "true"),
            exchange = @Exchange(name = "ex-test", durable = "true", type = "topic"),
            key = "test.*"
    )
    )
    @RabbitHandler
    public void receiver1(@Payload User user, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println(("receiver1 接收消息=====》》》》》{}" + user));
        //S 手工签收调用
        //注释掉则rabbitmq控制台queue中unack就不会自动清除
        //一般业务中手动签收
        //消费完消息后回送请求 channel.basicAck(deliverTag,multiple);
        //multiple是否支持批量签收
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliverTag, false);
        //E 手工签收调用
    }

    /**
     * 监听队列：qu-test
     * 交换机：ex-test
     * rountkey test.#
     * @param user
     * @param headers
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "qu-test", durable = "true"),
            exchange = @Exchange(name = "ex-test", durable = "true", type = "topic"),
            key = "test.#"
    )
    )
    @RabbitHandler
    public void receiver2(@Payload User user, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println(("receiver2  接收消息=====》》》》》{}" + user));
        //S 手工签收调用
        //注释掉则rabbitmq控制台queue中unack就不会自动清除
        //一般业务中手动签收
        //消费完消息后回送请求 channel.basicAck(deliverTag,multiple);
        //multiple是否支持批量签收
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliverTag, false);
        //E 手工签收调用
    }

    /**
     * 监听队列：qu-smsend
     * 交换机：ex-test
     * @param user
     * @param headers
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "qu-smsend", durable = "true"),
            exchange = @Exchange(name = "ex-test", durable = "true", type = ExchangeTypes.TOPIC),
            key = "smsend.#"
    )
    )
    @RabbitHandler
    public void smsend(@Payload User user, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println(("smsend 接收消息=====》》》》》{}" + user));
//        ((Receiver)AopContext.currentProxy()).
        //S 手工签收调用
        //注释掉则rabbitmq控制台queue中unack就不会自动清除
        //一般业务中手动签收
        //消费完消息后回送请求 channel.basicAck(deliverTag,multiple);
        //multiple是否支持批量签收
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliverTag, false);
        //E 手工签收调用
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "userNotice",durable = "true"),
            exchange = @Exchange(name = "userTopic",type = ExchangeTypes.TOPIC),
            key = "user.*"
    )
    )
    @RabbitHandler
    public void receiveDirect(@Payload String message){
        System.out.println("user.* 接收------》 "+ message);

    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.u1"),
            exchange = @Exchange(name = "userTopic",type = ExchangeTypes.TOPIC),
            key = "user.*"
    ))
    public void receiveDirect2(@Payload String message){
        System.out.println("user1 接收------》 "+ message);


    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.u2"),
            exchange = @Exchange(name = "userTopic",type = ExchangeTypes.TOPIC),
            key = "user.*"
    ))    public void receiveDirect3(@Payload String message){
        System.out.println("user2 接收------》 "+ message);


    }


}