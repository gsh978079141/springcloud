package com.gsh.springcloud.starter.mq.mqtt;

import com.gsh.springcloud.starter.mq.anotation.MqttEvent;
import com.gsh.springcloud.starter.mq.anotation.MyMqttListener;
import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import com.gsh.springcloud.starter.mq.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * @author gsh
 */
public abstract class AbstractMqttListener implements InitializingBean {

    @Autowired
    protected ApplicationContext applicationContext;

    @Resource
    private MqttProperties mqttProperties;

    @Resource
    private MqttPushClient mqttPushClient;

    @Value("${spring.profiles.active:native}")
    private String profile;

    @Value("${spring.application.name}")
    private String service;

    public AbstractMqttListener() { /* compiled code */ }

    public abstract String[] subscribe();

    public abstract void receiveMessage(MqttMessage message);

    @Override
    public void afterPropertiesSet() throws java.lang.Exception {
        EventRegister.regist(mqttProperties.getListenerPackage(), MyMqttListener.class, MqttEvent.class);
        mqttPushClient.connect(mqttProperties.getHostUrl(), mqttProperties.getClientId(), mqttProperties.getUsername(), mqttProperties.getPassword(), mqttProperties.getTimeout(), mqttProperties.getKeepalive());
        mqttPushClient.subscribe(mqttProperties.getDefaultTopic(), 2);
        String[] subscribe = subscribe();
        for (String s : subscribe) {
            mqttPushClient.subscribe(s, 2);
        }
    }
}