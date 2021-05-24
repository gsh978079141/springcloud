package com.gsh.springcloud.order.controller;

import com.gsh.springcloud.order.domain.entity.Order;
import com.gsh.springcloud.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/order")
public class OrderController {

  @Resource
  private OrderService orderService;

  @PostMapping("/placeOrder")
  public void placeOrder(@RequestBody Order order) {
    orderService.placeOrder(order);
  }

  @PostMapping("/localTransaction")
  public void localTransaction(@RequestBody Order order) {
    orderService.localTransaction(order);
  }

  @PostMapping("/localTransactionDistributed")
  public void localTransactionDistributed(@RequestBody Order order) {
    orderService.localTransactionDistributed(order);
  }

  @RequestMapping("/getHeader")
  public String getHeader() throws UnsupportedEncodingException {
    String token = URLEncoder.encode("管", "UTF-8");
    System.out.println("加密  encode  " + token);
    return orderService.getHeader(token);
  }

}

