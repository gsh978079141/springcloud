package com.gsh.springcloud.user.config;

import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author gsh
 * @Description mqtt相关配置信息
 * @Classname MqttConfig
 */
@Configuration
@ConfigurationProperties("spring.mqtt")
@Data
public class MqttConfig {

  @Resource
  private MqttPushClient mqttPushClient;
  /**
   * 用户名
   */
  private String username;


}
