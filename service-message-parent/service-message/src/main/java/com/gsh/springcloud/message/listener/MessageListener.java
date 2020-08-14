package com.gsh.springcloud.message.listener;

import com.gsh.springcloud.message.request.MessageReq;

/**
 * 消息中心 监听器
 */
public interface MessageListener {


  /**
   * 用户 登录加积分
   *
   * @param messageReq 消息体
   */
  void userLoginAddIntegral(MessageReq messageReq);


  /**
   * 用户 注册发放新人优惠券
   *
   * @param messageReq 消息体
   */
  void userRegisterIssueCoupons(MessageReq messageReq);


  /**
   * 订单 完成订单扣减库存
   *
   * @param messageReq 消息体
   */
  void orderCompleteDecreaseInventory(MessageReq messageReq);

}
