package com.gsh.springcloud.message.controller;

import com.gsh.springcloud.message.client.MessageClient;
import com.gsh.springcloud.message.request.MessageReq;
import com.gsh.springcloud.message.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: 消息中心控制器
 * @author: Gsh
 * @create: 2019-12-18 15:06
 **/
@RestController("/message")
public class MessageController implements MessageClient {

  @Resource
  private MessageService messageService;

  /**
   * 用户 登录加积分
   *
   * @param messageReq 消息体
   */
  @PostMapping("/userLoginAddIntegralSend")
  @Override
  public void userLoginAddIntegralSend(@RequestBody MessageReq messageReq) {
    messageService.userLoginAddIntegralSend(messageReq);
  }


  /**
   * 用户 注册发放新人优惠券
   *
   * @param messageReq 消息体
   */
  @PostMapping("/userRegisterIssueCouponsSend")
  @Override
  public void userRegisterIssueCouponsSend(MessageReq messageReq) {
    messageService.userRegisterIssueCouponsSend(messageReq);
  }


  /**
   * 订单 完成订单扣减库存
   *
   * @param messageReq 消息体
   */
  @PostMapping("/orderCompleteDecreaseInventorySend")
  @Override
  public void orderCompleteDecreaseInventorySend(MessageReq messageReq) {
    messageService.orderCompleteDecreaseInventorySend(messageReq);
  }
}
