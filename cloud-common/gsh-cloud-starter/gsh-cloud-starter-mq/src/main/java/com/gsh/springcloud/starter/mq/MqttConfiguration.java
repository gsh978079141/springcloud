package com.gsh.springcloud.starter.mq;

import com.gsh.springcloud.starter.mq.properties.MqttProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author gsh
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties.class)
@ConditionalOnProperty(name = "mqtt.enabled", matchIfMissing = true)
public class MqttConfiguration {

//  @Resource
//  private MqttPushClient mqttPushClient;
//
//  @Bean(name = "mqttPushClient")
//  public MqttPushClient mqttPushClient(MqttProperties mqttProperties) {
//    mqttPushClient.connect(mqttProperties.getHostUrl(), mqttProperties.getClientId(), mqttProperties.getUsername(), mqttProperties.getPassword(), mqttProperties.getTimeout(), mqttProperties.getKeepalive());
//    // 以/#结尾表示订阅所有以test开头的主题
//    mqttPushClient.subscribe(mqttProperties.getDefaultTopic(), 2);
//    return mqttPushClient;
//  }


}

