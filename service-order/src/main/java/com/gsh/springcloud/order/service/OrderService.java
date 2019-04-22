package com.gsh.springcloud.order.service;

import entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
public interface OrderService extends IService<Order> {
    String lcn();
}
