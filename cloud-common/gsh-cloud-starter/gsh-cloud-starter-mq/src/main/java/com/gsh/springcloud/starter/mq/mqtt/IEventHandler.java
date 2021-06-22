package com.gsh.springcloud.starter.mq.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 事件处理接口
 *
 * @author gsh
 */
public interface IEventHandler {
  /**
   * 处理程序
   * //   * @param params 参数
   *
   * @param mqttMessage 参数
   * @throws Exception
   */
//  void handle(List<?> params) throws Exception;
  void handle(MqttMessage mqttMessage) throws Exception;
}
