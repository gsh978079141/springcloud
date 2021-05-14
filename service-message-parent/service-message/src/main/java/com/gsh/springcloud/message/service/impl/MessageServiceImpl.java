package com.gsh.springcloud.message.service.impl;

import com.gsh.springcloud.message.listener.MessageChannelClient;
import com.gsh.springcloud.message.request.MessageReq;
import com.gsh.springcloud.message.service.MessageService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: 消息中心业务实现
 * @author: Gsh
 * @create: 2019-12-18 11:39
 **/
@Service
@EnableBinding(MessageChannelClient.class)
public class MessageServiceImpl implements MessageService {

  @Resource
  private MessageChannelClient messageChannelClient;

  @Override
  public void userLoginAddIntegralSend(MessageReq messageReq) {
    messageChannelClient.userLoginAddIntegralSend().send(MessageBuilder.withPayload(messageReq).build());
  }

  @Override
  public void userRegisterIssueCouponsSend(MessageReq messageReq) {
    messageChannelClient.userRegisterIssueCouponsSend()
            .send(MessageBuilder.withPayload(messageReq).build());
  }

  @Override
  public void orderCompleteDecreaseInventorySend(MessageReq messageReq) {
    messageChannelClient.orderCompleteDecreaseInventorySend()
            .send(MessageBuilder.withPayload(messageReq).build());
  }
}