package com.gsh.springcloud.message.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.io.IOException;

/**
 * 消息扩展接口
 *
 * @author gsh
 */
public interface MessageExpandService {
  /**
   * 发送消息，保存消息日志
   *
   * @param targetTopic      目标队列
   * @param msg              消息
   * @param isSaveMessageLog 是否保存消息日志
   */
  void sendMessage(String targetTopic, String msg, boolean isSaveMessageLog);

  /**
   * 手动签收信息
   *
   * @param channel            信道
   * @param message            消息
   * @param isUpdateMessageLog 是否更新消息日志
   * @throws IOException
   */
  void manualAckMessage(Channel channel, Message message, boolean isUpdateMessageLog) throws IOException;

  /**
   * 消息补偿
   *
   * @param channel 信道
   * @param message 消息
   * @throws IOException
   */
  void compensateMessage(Channel channel, Message message) throws IOException;

  /**
   * 确认消息到达指定队列
   * 但消息被 broker 接收到只能表示已经到达 MQ服务器，并不能保证消息一定会被投递到目标 queue 里,需要用到 returnCallback
   *
   * @param correlationData 对象内部只有一个 id 属性，用来表示当前消息的唯一性
   * @param ack             消息投递到broker 的状态，true表示成功
   * @param cause           表示投递失败的原因
   */
  void confirmMessage(CorrelationData correlationData, boolean ack, String cause);

  /**
   * 如果消息未能投递到目标 queue 里将触发回调 returnCallback
   * 一旦向 queue 投递消息未成功，这里一般会记录下当前消息的详细投递数据，方便后续做重发或者补偿等操作
   *
   * @param message    消息
   * @param replyCode  返回code
   * @param replyText  返回报文
   * @param exchange   交换机
   * @param routingKey routingKey
   */
  void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey);
}