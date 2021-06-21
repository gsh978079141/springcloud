package com.gsh.springcloud.user.service;

import com.gsh.springcloud.starter.mq.mqtt.MqttEvent;
import com.gsh.springcloud.starter.mq.mqtt.MyMqttTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author gsh
 */
@Slf4j
@MqttEvent
@Component
public class MqttConsumer {

  @MyMqttTopic("vision-iot/device/gsh/mqtt/test")
  public void info() {
    //处理业务代码
    log.info("收到topic消息！");
  }
}
