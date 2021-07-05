package com.gsh.springcloud.user.listener;

import com.alibaba.fastjson.JSON;
import com.gsh.springcloud.starter.mq.anotation.MqttEvent;
import com.gsh.springcloud.starter.mq.anotation.MyMqttListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @author gsh
 */
@Slf4j
@MqttEvent
@Component
public class MqttListener {

    //  @MyMqttListener(topic = "${mqtt.topic.test-topic}")
    @MyMqttListener(topic = "vision-iot/gsh/test")
    public void test(MqttMessage message) {
        //处理业务代码
        log.info("test！:{}", JSON.toJSONString(message));
    }

    //  @MyMqttListener(topic = "${mqtt.topic.subscribe-topic}")
    @MyMqttListener(topic = "vision-iot/gsh/subscribe")
    public void subscribe(MqttMessage message) {
        //处理业务代码
        log.info("subscribe！:{}", JSON.toJSONString(message));
    }
}
