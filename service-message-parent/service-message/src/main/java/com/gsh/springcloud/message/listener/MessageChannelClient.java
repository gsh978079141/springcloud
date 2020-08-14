package com.gsh.springcloud.message.listener;

import com.gsh.springcloud.message.constant.QueuesConstant;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 消息中心 通道配置
 */
public interface MessageChannelClient {

  /**
   * 用户 登录加积分
   *
   * @return
   */
  @Output(QueuesConstant.USER_LOGIN_ADD_INTEGRAL)
  MessageChannel userLoginAddIntegralSend();


  /**
   * 用户 注册发放优惠券
   *
   * @return
   */
  @Output(QueuesConstant.USER_REGISTER_ISSUE_COUPONS)
  MessageChannel userRegisterIssueCouponsSend();

  /**
   * 订单 完成订单扣减库存
   *
   * @return
   */
  @Output(QueuesConstant.ORDER_COMPLETE_DECREASE_INVENTORY)
  MessageChannel orderCompleteDecreaseInventorySend();

}
