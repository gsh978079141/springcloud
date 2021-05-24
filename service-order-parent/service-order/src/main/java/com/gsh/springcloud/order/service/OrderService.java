package com.gsh.springcloud.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsh.springcloud.order.domain.entity.Order;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
public interface OrderService extends IService<Order> {

  /**
   * 分布式事务+fegin
   * 下单并减少用户积分
   */
  void placeOrder(Order order);

  /**
   * 本地事务
   *
   * @param order
   */
  void localTransaction(Order order);

  /**
   * 本地事务+fegin
   *
   * @param order
   */
  void localTransactionDistributed(Order order);

  List<Order> selectList();

  String getHeader(String token) throws UnsupportedEncodingException;
}
