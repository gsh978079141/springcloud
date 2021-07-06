package com.gsh.springcloud.starter.mq.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @author gsh
 */
@Slf4j
@Component
public class MessageHandler {

  public static void handleMqttMessage(String topic, MqttMessage message) throws Exception {
//  public static void handleMqttMessage(String topic, String message) throws Exception {

//        MqttMessage message1 = JSON.parseObject(message, MqttMessage.class);

//        if (isNotValidMessage(message1)) {
//            return;
//        }
//    EventRegister.emit(topic, new ArrayList<Object>() {{
//      add(message);
//    }});
//        EventRegister.emit("mqtt." + message.getCmd(), new ArrayList<Object>() {{
    EventRegister.emit(topic, message);
  }
}