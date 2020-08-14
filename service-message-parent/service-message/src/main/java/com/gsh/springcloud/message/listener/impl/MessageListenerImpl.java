package com.gsh.springcloud.message.listener.impl;

import com.gsh.springcloud.message.constant.QueuesConstant;
import com.gsh.springcloud.message.listener.MessageListener;
import com.gsh.springcloud.message.request.MessageReq;
import com.gsh.springcloud.user.client.UserClient;
import com.gsh.springcloud.user.request.UserReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: 消息中心 监听器 实现
 * @author: Gsh
 * @create: 2019-12-18 15:59
 **/
@Log4j2
public class MessageListenerImpl implements MessageListener {

  @Resource
  private UserClient userClient;

  @StreamListener(value = QueuesConstant.USER_LOGIN_ADD_INTEGRAL)
  @Override
  public void userLoginAddIntegral(@Payload MessageReq messageReq) {
    log.info("监听 开始userLoginAddIntegral 消息，执行加积分");
    UserReq user = new UserReq();
    user.setId(messageReq.getUserId());
    user.setIntegral(messageReq.getIntegral());
    userClient.updateById(user);
    log.info("监听 结束 userLoginAddIntegral 消息，积分更新成功");

  }


  @StreamListener(value = QueuesConstant.USER_REGISTER_ISSUE_COUPONS)
  @Override
  public void userRegisterIssueCoupons(@Payload MessageReq messageReq) {

  }


  @StreamListener(value = QueuesConstant.ORDER_COMPLETE_DECREASE_INVENTORY)
  @Override
  public void orderCompleteDecreaseInventory(@Payload MessageReq messageReq) {

  }


}
