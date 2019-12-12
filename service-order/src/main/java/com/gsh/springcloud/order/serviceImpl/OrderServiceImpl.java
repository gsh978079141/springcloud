package com.gsh.springcloud.order.serviceImpl;

import entity.Order;
import com.gsh.springcloud.order.dao.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsh.springcloud.order.service.OrderFeginClient;
import com.gsh.springcloud.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderFeginClient orderFeginClient;

    /**
     * 下单
     * @param
     * @return
     */
    @Transactional
    @Override
    public String lcn() {
        Order order = new Order();
        order.setNum("lcnTest");
        order.setUserId(1);
        order.setId(2);
        orderMapper.insert(order);
        orderFeginClient.lcnTest();
        int a = 1/0;
        return "success";
    }
}
