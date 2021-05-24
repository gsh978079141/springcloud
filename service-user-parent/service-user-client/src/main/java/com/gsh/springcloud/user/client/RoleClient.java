package com.gsh.springcloud.user.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 角色管理client
 *
 * @author gsh
 */
@Api(tags = "RoleAPI", value = "角色管理API")
//@FeignClient(url = "${feign.url.service-user:http://127.0.0.1:7003}", path = "/users", name = "userClient")
@FeignClient(name = "service-user", path = "/roles")
public interface RoleClient {

  @ApiOperation("测试 登录发放优惠卷")
  @GetMapping("/all")
  void all();

}
