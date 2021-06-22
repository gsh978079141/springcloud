package com.gsh.springcloud.starter.mq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gsh
 * @Description mqtt相关配置信息
 * @Classname MqttConfig
 */
@Configuration
@ConfigurationProperties("spring.mqtt")
@Data
public class MqttProperties {

  /**
   * 用户名
   */
  private String username;
  /**
   * 密码
   */
  private String password;
  /**
   * 连接地址
   */
  private String hostUrl;
  /**
   * 客户Id
   */
  private String clientId;
  /**
   * 默认连接话题
   */
  private String defaultTopic;
  /**
   * 超时时间
   */
  private int timeout;
  /**
   * 保持连接数
   */
  private int keepalive;

  /**
   * 自定义配置：Listener扫描的包路径
   */
  private String listenerPackage;

}
