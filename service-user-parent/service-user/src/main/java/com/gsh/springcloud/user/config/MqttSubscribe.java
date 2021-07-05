package com.gsh.springcloud.user.config;

import com.gsh.springcloud.starter.mq.mqtt.AbstractMqttListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description:
 * @author: Gsh
 * @create: 2021-06-23 11:34
 **/
@Component
@Slf4j
public class MqttSubscribe extends AbstractMqttListener {

    @Resource
    private MqttTopicProperties mqttTopicProperties;

    @Override
    public String[] subscribe() {
        return new String[]{mqttTopicProperties.getSubscribeTopic(), mqttTopicProperties.getTestTopic()};
    }

    @Override
    public void receiveMessage(MqttMessage message) {

    }
}
