package com.gsh.springcloud.message.client;

import com.gsh.springcloud.message.request.MessageReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息中心-client
 */
@FeignClient(name = "messageClient")
public interface MessageClient {

  /**
   * 用户 登录加积分
   *
   * @param messageReq 消息体
   */
  @PostMapping("/userLoginAddIntegralSend")
  void userLoginAddIntegralSend(@RequestBody MessageReq messageReq);

  /**
   * 用户 注册发放新人优惠券
   *
   * @param messageReq 消息体
   */
  @PostMapping("/userRegisterIssueCouponsSend")
  void userRegisterIssueCouponsSend(MessageReq messageReq);


  /**
   * 订单 完成订单扣减库存
   *
   * @param messageReq 消息体
   */
  @PostMapping("/orderCompleteDecreaseInventorySend")
  void orderCompleteDecreaseInventorySend(MessageReq messageReq);

}
