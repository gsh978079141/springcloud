package com.gsh.springcloud.order.client;

import com.gsh.springcloud.order.dto.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;

/**
 * @author gsh
 */
@Api(tags = "OrderAPI", value = "订单管理API")
//@FeignClient(url = "${feign.url.service-order:http://127.0.0.1:7004}", path = "/orders", name = "orderClient")
@FeignClient(name = "service-order", path = "/orders")
public interface OrderClient {

  @ApiOperation("分布式事务测试")
  @PostMapping("/placeOrder")
  void placeOrder(@RequestBody OrderDto order);

  @ApiOperation("本地事务")
  @PostMapping("/localTransaction")
  void localTransaction(@RequestBody OrderDto order);

  @ApiOperation("本地事务")
  @PostMapping("/localTransactionDistributed")
  void localTransactionDistributed(@RequestBody OrderDto order);

  @ApiOperation("获取头部")
  @GetMapping("/getHeader")
  String getHeader() throws UnsupportedEncodingException;

  @ApiOperation("测试股")
  @GetMapping("/test")
  String test();

}
