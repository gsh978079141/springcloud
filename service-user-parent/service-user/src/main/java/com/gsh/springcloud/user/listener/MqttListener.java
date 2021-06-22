package com.gsh.springcloud.user.listener;

import com.alibaba.fastjson.JSON;
import com.gsh.springcloud.starter.mq.anotation.MqttEvent;
import com.gsh.springcloud.starter.mq.anotation.MyMqttListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @author gsh
 */
@Slf4j
@MqttEvent
@Component
public class MqttListener {

  @MyMqttListener(topic = "vision-iot/device/gsh/mqtt/test1")
  public void test1(MqttMessage message) {
    //处理业务代码
    log.info("test1！:{}", JSON.toJSONString(message));
  }

  @MyMqttListener(topic = "vision-iot/device/gsh/mqtt/test2")
  public void test2(MqttMessage message) {
    //处理业务代码
    log.info("test2！:{}", JSON.toJSONString(message));
  }
}
