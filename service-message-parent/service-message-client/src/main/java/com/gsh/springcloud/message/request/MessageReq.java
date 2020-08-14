package com.gsh.springcloud.message.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springcloud
 * @description: 用户积分
 * @author: Gsh
 * @create: 2019-12-18 14:14
 **/
@Data
@NoArgsConstructor
public class MessageReq {

  /**
   * 用户id
   */
  private Integer userId;
  /**
   * 积分变化值
   */
  private Integer integral;
  /**
   * 订单id
   */
  private Integer orderId;

  /**
   * 订单号
   */
  private String orderNum;
  /**
   * 商品id
   */
  private Integer goodId;


}
