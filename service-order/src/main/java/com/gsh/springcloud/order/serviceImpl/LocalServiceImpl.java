package com.gsh.springcloud.order.serviceImpl;

import com.codingapi.tx.annotation.TxTransaction;
import com.gsh.springcloud.common.entity.Order;
import com.gsh.springcloud.order.dao.OrderMapper;
import com.gsh.springcloud.order.service.LocalService;
import com.gsh.springcloud.order.service.OrderFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalServiceImpl implements LocalService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderFeginClient orderFeginClient;


    @TxTransaction(isStart = true)
    @Transactional
    @Override
    public boolean lcn() {
        Order order = new Order();
        order.setNum("lcnTest");
        order.setUserId(1);
        order.setId(2);
        orderMapper.insert(order);
        orderFeginClient.lcnTest();
        int a = 1/0;
        return true;
    }


}
