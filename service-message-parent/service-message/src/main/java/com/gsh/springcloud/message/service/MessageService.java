package com.gsh.springcloud.message.service;

import com.gsh.springcloud.message.request.MessageReq;

/**
 * 消息中心业务接口
 */
public interface MessageService {


  /**
   * 用户 登录加积分
   *
   * @param messageReq 消息体
   */
  void userLoginAddIntegralSend(MessageReq messageReq);


  /**
   * 用户 注册发放新人优惠券
   *
   * @param messageReq 消息体
   */
  void userRegisterIssueCouponsSend(MessageReq messageReq);


  /**
   * 订单 完成订单扣减库存
   *
   * @param messageReq 消息体
   */
  void orderCompleteDecreaseInventorySend(MessageReq messageReq);
}
