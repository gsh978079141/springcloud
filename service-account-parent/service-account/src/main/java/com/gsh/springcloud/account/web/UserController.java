package com.gsh.springcloud.account.web;


import com.gsh.springcloud.account.client.UserClient;
import com.gsh.springcloud.account.dto.AccessTokenDto;
import com.gsh.springcloud.account.request.*;
import com.gsh.springcloud.account.response.ExistenceResp;
import com.gsh.springcloud.account.response.UserListResp;
import com.gsh.springcloud.account.response.UserResp;
import com.gsh.springcloud.account.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/users")
public class UserController implements UserClient {

  @Resource
  UserService userService;

  @Override
  public void create(@Validated @RequestBody UserCreateReq req) {
    userService.create(req);
  }

  @Override
  public void createInBatch(@Validated @RequestBody UserCreateBatchReq req) {
    userService.createInBatch(req);
  }

  @Override
  public UserListResp search(@Validated UserSearchReq req) {
    return userService.searchByPage(req);
  }

  @Override
  public UserListResp all() {
    return userService.getAllUsers();
  }

  @Override
  public void deleteById(@PathVariable String id) {
    userService.deleteById(id);
  }

  @Override
  public void updateById(@PathVariable String id, @RequestBody @Validated UserUpdateReq req) {
    userService.update(id, req);
  }

  @Override
  public void bindRoles4user(@PathVariable String id, @RequestBody @Validated UserRolesBindReq req) {
    userService.bindRoles4user(id, req.getRoles());
  }

  @Override
  public void bindClientRoles4user(String id, @PathVariable("client-id") String clientId, @RequestBody UserRolesBindReq req) {
    userService.bindClientRoles4user(id, clientId, req.getRoles());
  }


  @Override
  public void unbindClientRoles4user(String id, @PathVariable("client-id") String clientId, @RequestBody UserRolesBindReq req) {
    userService.unbindClientRoles4user(id, clientId, req.getRoles());
  }

  @Override
  public UserResp getByUsername(@PathVariable String username) {
    return userService.getByUsername(username);
  }

  @Override
  public UserResp getWithParams(String username, String email) {
    return userService.getWithParams(username, email);
  }

  @Override
  public AccessTokenDto getAccessToken(String userName, String password) {
    return userService.getAccessToken(userName, password);
  }

  @Override
  public void resetPassword(String userId, String newPassword) {
    userService.resetPassword(userId, newPassword);
  }

  @Override
  public UserResp getById(@PathVariable String id) {
    return userService.getById(id);
  }

  @Override
  public UserListResp getByIds(String[] id) {
    return userService.getByIds(id);
  }

  @Override
  public ExistenceResp checkExistence(String id, String username, String email) {
    return userService.checkExistence(id, username, email);
  }
}
