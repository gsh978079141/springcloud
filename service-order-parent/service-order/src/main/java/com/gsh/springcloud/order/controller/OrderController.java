package com.gsh.springcloud.order.controller;

import com.gsh.springcloud.order.client.OrderClient;
import com.gsh.springcloud.order.domain.converter.OrderConverter;
import com.gsh.springcloud.order.dto.OrderDto;
import com.gsh.springcloud.order.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
@RestController
@RequestMapping("/orders")
public class OrderController implements OrderClient {

  @Resource
  private OrderService orderService;

  @Resource
  private OrderConverter orderConverter;

  @Override
  public void placeOrder(OrderDto orderDto) {
    orderService.placeOrder(orderConverter.dto2entity(orderDto));
  }

  @Override
  public void localTransaction(OrderDto orderDto) {
    orderService.localTransaction(orderConverter.dto2entity(orderDto));
  }

  @Override
  public void localTransactionDistributed(OrderDto orderDto) {
    orderService.localTransactionDistributed(orderConverter.dto2entity(orderDto));
  }

  @Override
  public String getHeader() throws UnsupportedEncodingException {
    String token = URLEncoder.encode("管", "UTF-8");
    System.out.println("加密  encode  " + token);
    return orderService.getHeader(token);
  }

  @Override
  public String test() {
    return "success";
  }

}

