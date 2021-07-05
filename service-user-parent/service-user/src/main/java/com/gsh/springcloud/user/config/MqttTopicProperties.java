package com.gsh.springcloud.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gsh
 * @Description 自定义mqtt-topic相关配置
 */
@Configuration
@ConfigurationProperties("mqtt.topic")
@Data
public class MqttTopicProperties {

  private String testTopic;

    private String subscribeTopic;

}
