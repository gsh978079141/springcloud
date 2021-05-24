package com.gsh.springcloud.message.listener.impl;

import com.alibaba.fastjson.JSON;
import com.gsh.springcloud.message.domain.dto.MessageLogDto;
import com.gsh.springcloud.message.domain.entity.MessageLog;
import com.gsh.springcloud.message.listener.MessageExpandService;
import com.gsh.springcloud.message.service.MessageLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @program: EDU_DBP_APP
 * @description: 消息功能扩展接口
 * @author: Gsh
 * @create: 2021-04-21 15:56
 **/
@Service
@Slf4j
public class MessageExpandServiceImpl implements MessageExpandService {

  @Resource
  private RabbitTemplate rabbitTemplate;

  @Resource
  private MessageLogService messageLogService;

  @Value("${spring.application.name}")
  private String applicationName;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void sendMessage(String targetTopic, String msg, boolean isSaveMessageLog) {
    if (!isSaveMessageLog) {
      rabbitTemplate.convertAndSend(targetTopic, msg);
      log.info("send to rabbitmq without messageLog----> topic :{}; message :{}", targetTopic, msg);
    } else {
      // 消息uuid
      String uuid = UUID.randomUUID().toString();
      // 保存消息日志
      messageLogService.save(MessageLog.builder()
              .uuid(uuid)
              .msg(msg)
              .senderName(applicationName)
              .status(MessageLogDto.Status.READY.getCode())
              .targetTopic(targetTopic).build());
      // 发送消息
      MessageProperties messageProperties = new MessageProperties();
      messageProperties.setMessageId(uuid);
      Message message = new Message(msg.getBytes(), messageProperties);
      rabbitTemplate.convertAndSend(targetTopic, message, new CorrelationData(uuid));
      log.info("send to rabbitmq with messageLog----> topic :{}; message :{}", targetTopic, JSON.toJSONString(message));
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void manualAckMessage(Channel channel, Message message, boolean isUpdateMessageLog) throws IOException {
    String messageId = message.getMessageProperties().getMessageId();
    log.info("manual_ack_message： message_id : [{}] ; message: [{}]", messageId, message);
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    if (isUpdateMessageLog && StringUtils.isNotBlank(messageId)) {
      messageLogService.updateByUuid(MessageLogDto.builder().receiverName(applicationName).status(MessageLogDto.Status.SUCCESS.getCode()).receiveTime(new Date()).build(), messageId);
    }
  }

  @Override
  public void compensateMessage(Channel channel, Message message) throws IOException {
    if (message.getMessageProperties().getRedelivered()) {
      log.error("消息已重复处理失败,拒绝再次接收...");
      // 拒绝消息
      channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    } else {
      String messageUuid = message.getMessageProperties().getMessageId();
      MessageLogDto messageLogDto = messageLogService.getOneByUuid(messageUuid);
      if (MessageLogDto.Status.MANUAL.getCode().equals(messageLogDto.getStatus())) {
        log.error("消息重试已超过最大次数，请人工排查问题message_log_uuid:{}", messageUuid);
        return;
      }
      // 重新发送消息到队尾
      // 如仍无法满足，需结合mysql或redis消息持久化方案
      log.error("消息即将再次返回队列处理...");
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
              message.getMessageProperties().getReceivedRoutingKey(), com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN,
              JSON.toJSONBytes(message.getBody()));
      messageLogService.updateRetryInfoByUuid(messageUuid);
    }
  }

  @Override
  public void confirmMessage(CorrelationData correlationData, boolean ack, String cause) {
    if (!ack) {
      log.error("消息发送至指定队列-异常，correlationData={} ,ack={}, cause={}", correlationData, ack, cause);
    } else {
      if (Objects.nonNull(correlationData) && StringUtils.isNotBlank(correlationData.getId())) {
        log.info("消息发送至指定队列-已成功，correlationData={} ,ack={}, cause={}", correlationData, ack, cause);
        messageLogService.updateByUuid(MessageLogDto.builder().status(MessageLogDto.Status.UNACKED.getCode()).sendTime(new Date()).build(), correlationData.getId());
      }
    }
  }

  @Override
  public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
    if (StringUtils.isBlank(routingKey)) {
      return;
    }
    log.info("returnedMessage ===> replyCode={} ,replyText={} ,exchange={} ,routingKey={}", replyCode, replyText, exchange, routingKey);
  }


}
