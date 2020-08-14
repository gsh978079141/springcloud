package com.gsh.springcloud.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.springcloud.order.model.Order;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
public interface OrderMapper extends BaseMapper<Order> {
  boolean insertTest(Order order);
}
