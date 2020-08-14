package com.gsh.springcloud.user.client;

import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务client
 */
@FeignClient(name = "userClient")
public interface UserClient {

  /**
   * 测试 登录发放优惠卷
   *
   * @param user
   */
  @GetMapping("/login")
  void login(UserReq user);

  @GetMapping("/find")
  UserResp find(UserReq user);

  @PutMapping("/save")
  void save(UserReq user);

  @PostMapping("/updateById")
  void updateById(@RequestBody UserReq user);

  @DeleteMapping("/deleteById")
  void deleteById(UserReq user);

  /**
   * 例
   * 头部传参
   *
   * @param token
   * @return
   */
  @GetMapping("/user/getHeader")
  String getHeader(@RequestHeader("token") String token);


}
