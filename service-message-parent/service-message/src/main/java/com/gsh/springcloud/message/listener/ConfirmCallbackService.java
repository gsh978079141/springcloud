package com.gsh.springcloud.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 实现接口 ConfirmCallback ，重写其confirm()方法，方法内有三个参数correlationData、ack、cause。
 * correlationData：对象内部只有一个 id 属性，用来表示当前消息的唯一性。
 * ack：消息投递到broker 的状态，true表示成功。
 * cause：表示投递失败的原因。
 * 但消息被 broker 接收到只能表示已经到达 MQ服务器，并不能保证消息一定会被投递到目标 queue 里,需要用到 returnCallback 。
 *
 * @author gsh
 */
@Slf4j
@Component
public class ConfirmCallbackService implements RabbitTemplate.ConfirmCallback {

  @Resource
  private MessageExpandService messageExpandService;

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    messageExpandService.confirmMessage(correlationData, ack, cause);
  }
} 