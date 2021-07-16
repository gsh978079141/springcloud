package com.gsh.springcloud.user.client;

import com.gsh.springcloud.user.fallback.UserClientFallback;
import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务client
 * @author gsh
 */
@Api(tags = "UserAPI", value = "用户管理API")
//@FeignClient(url = "${feign.url.service-user:http://127.0.0.1:7003}", path = "/users", name = "userClient")
@FeignClient(name = "service-user", path = "/user/users",fallback = UserClientFallback.class)
public interface UserClient {

  @ApiOperation("测试 登录发放优惠卷")
  @GetMapping("/login")
  void login(UserReq user);

  @ApiOperation("查询")
  @GetMapping("/find")
  UserResp find(UserReq user);

  @ApiOperation("保存")
  @PutMapping("/save")
  void save(UserReq user);

  @ApiOperation("更新")
  @PostMapping("/updateById")
  void updateById(@RequestBody UserReq user);

  @ApiOperation("删除")
  @DeleteMapping("/deleteById")
  void deleteById(UserReq user);

  @ApiOperation("头部传参")
  @GetMapping("/user/getHeader")
  String getHeader(@RequestHeader("token") String token);

  @ApiOperation("sentinel 测试")
  @GetMapping("/user/test")
  void test();



}
