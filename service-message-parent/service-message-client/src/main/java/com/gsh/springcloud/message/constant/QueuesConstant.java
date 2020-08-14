package com.gsh.springcloud.message.constant;

/**
 * @program: springcloud
 * @description: 队列信息常量名
 * @author: Gsh
 * @create: 2019-04-22 15:03
 **/
public interface QueuesConstant {

  /**
   * 用户 注册发优惠券
   */
  String USER_REGISTER_ISSUE_COUPONS = "user_register_issue_coupons";
  /**
   * 用户 登录加积分
   */
  String USER_LOGIN_ADD_INTEGRAL = "user_login_add_integral";

  /**
   * 订单 完成减库存
   */
  String ORDER_COMPLETE_DECREASE_INVENTORY = "order_complete_decrease_inventory";
}
