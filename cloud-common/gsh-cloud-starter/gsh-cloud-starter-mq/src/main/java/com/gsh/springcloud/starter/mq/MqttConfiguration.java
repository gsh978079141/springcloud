package com.gsh.springcloud.starter.mq;

import com.gsh.springcloud.starter.mq.anotation.MqttEvent;
import com.gsh.springcloud.starter.mq.anotation.MyMqttListener;
import com.gsh.springcloud.starter.mq.config.MqttPushCallback;
import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import com.gsh.springcloud.starter.mq.mqtt.EventRegister;
import com.gsh.springcloud.starter.mq.properties.MqttProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author gsh
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties.class)
@ConditionalOnProperty(prefix = "spring.mqtt", name = "host-url")
@Import({MqttPushClient.class, MqttPushCallback.class})
public class MqttConfiguration {

  @Resource
  private MqttProperties mqttProperties;

  @Resource
  private MqttPushClient mqttPushClient;

  @PostConstruct
  public void init() {
    try {
      EventRegister.regist(mqttProperties.getListenerPackage(), MyMqttListener.class, MqttEvent.class);
      mqttPushClient.connect(mqttProperties.getHostUrl(), mqttProperties.getClientId(), mqttProperties.getUsername(), mqttProperties.getPassword(), mqttProperties.getTimeout(), mqttProperties.getKeepalive());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

