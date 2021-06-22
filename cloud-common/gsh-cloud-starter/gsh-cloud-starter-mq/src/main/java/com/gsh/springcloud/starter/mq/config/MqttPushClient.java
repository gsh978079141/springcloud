package com.gsh.springcloud.starter.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author gsh
 * @Description mqtt推送客户端
 * @Classname MqttPushClient
 */
@Component
@Slf4j
public class MqttPushClient {

  @Resource
  private MqttPushCallback mqttPushCallback;

  private static MqttClient client;

  private static MqttClient getClient() {
    return client;
  }

  private static void setClient(MqttClient client) {
    MqttPushClient.client = client;
  }


  /**
   * 客户端连接
   *
   * @param host      ip+端口
   * @param clientID  客户端Id
   * @param username  用户名
   * @param password  密码
   * @param timeout   超时时间
   * @param keepalive 保留数
   */
  public void connect(String host, String clientID, String username, String password, int timeout, int keepalive) {
    MqttClient client;
    try {
      client = new MqttClient(host, clientID, new MemoryPersistence());
      MqttConnectOptions options = new MqttConnectOptions();
      options.setCleanSession(true);
      options.setUserName(username);
      options.setPassword(password.toCharArray());
      options.setConnectionTimeout(timeout);
      options.setKeepAliveInterval(keepalive);
      MqttPushClient.setClient(client);
      try {
        client.setCallback(mqttPushCallback);
        client.connect(options);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 发布
   *
   * @param qos         连接方式
   * @param retained    是否保留
   * @param topic       主题
   * @param pushMessage 消息体
   */
  public void publish(int qos, boolean retained, String topic, String pushMessage) {
    MqttMessage message = new MqttMessage();
    message.setQos(qos);
    message.setRetained(retained);
    message.setPayload(pushMessage.getBytes());
    MqttTopic mTopic = MqttPushClient.getClient().getTopic(topic);
    if (null == mTopic) {
      log.error("topic not exist");
    }
    MqttDeliveryToken token;
    try {
      assert mTopic != null;
      token = mTopic.publish(message);
      token.waitForCompletion();
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  /**
   * 订阅某个主题
   *
   * @param topic 主题
   * @param qos   连接方式
   */
  public void subscribe(String topic, int qos) {
    log.info("subscribe: " + topic);
    try {
      MqttPushClient.getClient().subscribe(topic, qos);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  @PostConstruct
  public void init() {
//    this.connect();
  }

}