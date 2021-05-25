package com.gsh.springcloud.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsh.springcloud.order.domain.entity.Order;
import com.gsh.springcloud.order.domain.mapper.OrderMapper;
import com.gsh.springcloud.order.service.OrderService;
import com.gsh.springcloud.user.client.UserClient;
import com.gsh.springcloud.user.request.UserReq;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

  @Resource
  private OrderMapper orderMapper;

  @Resource
  private UserClient userClient;

  @GlobalTransactional(timeoutMills = 300000, name = "gsh-cloud-seata-example")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void placeOrder(Order order) {
//        String method = "分布式事务+fegin";
    String method = order.getName();
    order.setName(method);
    log.info("{}:开始 订单入库", method);
    orderMapper.insert(order);
    log.info("{}:成功  本地事务订单入库", method);
    UserReq u = new UserReq();
    u.setId(order.getUserId());
    u.setUserName(method);
    double integral = Double.parseDouble(new DecimalFormat("0").format(order.getPrice() / 10));
    u.setIntegral(integral);
    log.info("{}：开始  更新用户", method);
    userClient.updateById(u);
    log.info("{}：成功  更新用户", method);
    if (0 == order.getUserId()) {
      int a = 1 / 0;
    }
  }


  @Transactional(rollbackFor = Exception.class)
  @Override
  public void localTransaction(Order order) {
//        String method = "本地事务";
    String method = order.getName();
    log.info("{}:开始 订单入库", method);
    order.setName(method);
    orderMapper.insert(order);
    log.info("{}:成功 订单入库", method);
    if (0 == order.getUserId()) {
      int a = 1 / 0;
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void localTransactionDistributed(Order order) {
//        String method = "本地事务+fegin";
    String method = order.getName();
    order.setName(method);
    log.info("{}}:开始 订单入库", method);
    orderMapper.insert(order);
    log.info("{}:成功  本地事务订单入库", method);
    UserReq u = new UserReq();
    u.setId(order.getUserId());
    u.setUserName(method);
    double integral = Double.parseDouble(new DecimalFormat("0").format(order.getPrice() / 10));
    u.setIntegral(integral);
    log.info("{}：开始  更新用户", method);
    userClient.updateById(u);
    log.info("{}：成功  更新用户", method);
    if (0 == order.getUserId()) {
      int a = 1 / 0;
    }

  }

  @Override
  public List<Order> selectList() {
    return orderMapper.selectList(new QueryWrapper<>());
  }

  @Override
  public String getHeader(String token) throws UnsupportedEncodingException {
    return null;
  }

}
