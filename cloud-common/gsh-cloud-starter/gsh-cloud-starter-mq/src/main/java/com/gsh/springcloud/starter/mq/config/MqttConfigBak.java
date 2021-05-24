//package com.gsh.springcloud.starter.mq.config;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @Description mqtt相关配置信息
// * @Classname MqttConfig
// * @author gsh
// */
//@Component
//@ConfigurationProperties("spring.mqtt")
//@Data
//public class MqttConfigBak {
//
//  @Resource
//  private MqttPushClient mqttPushClient;
//
//  /**
//   * 用户名
//   */
//  private String username;
//  /**
//   * 密码
//   */
//  private String password;
//  /**
//   * 连接地址
//   */
//  private String hostUrl;
//  /**
//   * 客户Id
//   */
//  private String clientId;
//  /**
//   * 默认连接话题
//   */
//  private String defaultTopic;
//  /**
//   * 超时时间
//   */
//  private int timeout;
//  /**
//   * 保持连接数
//   */
//  private int keepalive;
//
//  @Bean
//  public MqttPushClient getMqttPushClient() {
//    mqttPushClient.connect(hostUrl, clientId, username, password, timeout, keepalive);
//    // 以/#结尾表示订阅所有以test开头的主题
////    mqttPushClient.subscribe(K12Properties.callbackTopic, 2);
//    mqttPushClient.subscribe("callbackTopic", 2);
//    return mqttPushClient;
//  }
//
//}
