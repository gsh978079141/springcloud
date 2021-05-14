package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.dto.AccessTokenDto;
import com.gsh.springcloud.account.request.*;
import com.gsh.springcloud.account.response.ExistenceResp;
import com.gsh.springcloud.account.response.UserListResp;
import com.gsh.springcloud.account.response.UserResp;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "${feign.url.service-account}/users", name = "userClient")
@Api(tags = "User API", value = "user")
public interface UserClient {

  @PostMapping
  void create(@RequestBody UserCreateReq req);

  @PostMapping("batch")
  void createInBatch(@RequestBody UserCreateBatchReq req);

  @GetMapping
  UserListResp search(@Validated UserSearchReq req);

  @GetMapping("/all")
  UserListResp all();

  @DeleteMapping("{id}")
  void deleteById(@PathVariable("id") String id);

  @PutMapping("{id}")
  void updateById(@PathVariable("id") String id, @RequestBody UserUpdateReq req);

  @PutMapping("{id}/role-binds")
  @Deprecated
  void bindRoles4user(@PathVariable("id") String id, @RequestBody UserRolesBindReq req);

  @PutMapping("{id}/role-binds/clients/{client-id}")
  void bindClientRoles4user(@PathVariable("id") String id, @PathVariable("client-id") String clientId, @RequestBody UserRolesBindReq req);

  @PutMapping("{id}/role-unbinds/clients/{client-id}")
  void unbindClientRoles4user(@PathVariable("id") String id, @PathVariable("client-id") String clientId, @RequestBody UserRolesBindReq req);

  @GetMapping("username-{username}")
  UserResp getByUsername(@PathVariable("username") String username);

  @GetMapping("single")
  UserResp getWithParams(@RequestParam(value = "username", required = false) String username,
                         @RequestParam(value = "email", required = false) String email);

  @GetMapping("getAccessToken")
  AccessTokenDto getAccessToken(@RequestParam("userName") String userName, @RequestParam("password") String password);

  @PutMapping("reset-password")
  void resetPassword(@RequestParam("userId") String userId, @RequestParam("newPassword") String newPassword);

  @GetMapping("{id}")
  UserResp getById(@PathVariable("id") String id);

  @GetMapping("list")
  UserListResp getByIds(@RequestParam("ids") String[] id);


  @GetMapping("existences")
  ExistenceResp checkExistence(
          @RequestParam(value = "id", required = false) String id,
          @RequestParam(value = "username", required = false) String username,
          @RequestParam(value = "email", required = false) String email);


}
