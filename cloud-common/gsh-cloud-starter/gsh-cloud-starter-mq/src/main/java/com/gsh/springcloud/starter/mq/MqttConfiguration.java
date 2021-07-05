package com.gsh.springcloud.starter.mq;

import com.gsh.springcloud.starter.mq.config.MqttPushCallback;
import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import com.gsh.springcloud.starter.mq.properties.MqttProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author gsh
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties.class)
@ConditionalOnProperty(prefix = "spring.mqtt", name = "host-url")
@Import({MqttPushClient.class, MqttPushCallback.class})
public class MqttConfiguration {

}

