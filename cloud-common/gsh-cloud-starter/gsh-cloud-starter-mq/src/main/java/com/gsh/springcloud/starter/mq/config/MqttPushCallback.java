package com.gsh.springcloud.starter.mq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gsh
 */
@Component
public class MqttPushCallback implements MqttCallback {

  @Override
  public void connectionLost(Throwable throwable) {
    // 连接丢失后，一般在这里面进行重连
//    log.info("MQTT disconnect");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void messageArrived(String topic, MqttMessage mqttMessage) {
    // subscribe后得到的消息会执行到这里面
//    log.info("mqtt recv topic={} payload={}", topic, new String(mqttMessage.getPayload()));
    JSONObject obj = JSON.parseObject(new String(mqttMessage.getPayload()));
    String examQueryId = obj.getString("examQueryID");
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//    log.info("deliveryComplete: " + iMqttDeliveryToken.isComplete());
  }
}