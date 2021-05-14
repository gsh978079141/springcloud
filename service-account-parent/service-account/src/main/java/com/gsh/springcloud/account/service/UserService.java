package com.gsh.springcloud.account.service;


import com.gsh.springcloud.account.dto.AccessTokenDto;
import com.gsh.springcloud.account.request.UserCreateBatchReq;
import com.gsh.springcloud.account.request.UserCreateReq;
import com.gsh.springcloud.account.request.UserSearchReq;
import com.gsh.springcloud.account.request.UserUpdateReq;
import com.gsh.springcloud.account.response.ExistenceResp;
import com.gsh.springcloud.account.response.UserListResp;
import com.gsh.springcloud.account.response.UserResp;

import java.util.List;
import java.util.Optional;

public interface UserService {


  void create(UserCreateReq req);

  void update(String id, UserUpdateReq req);

  void disable(String id);

  void deleteById(String id);

  UserResp getById(String id);

  Optional<UserResp> getByUserName(String username);

  UserListResp searchByPage(UserSearchReq req);

  UserListResp getAllUsers();

  void bindRoles4user(String id, List<String> roles);

  void bindClientRoles4user(String id, String clientId, List<String> roles);

  void unbindClientRoles4user(String id, String clientId, List<String> roles);

  UserResp getByUsername(String username);

  AccessTokenDto getAccessToken(String userId, String password);

  void createInBatch(UserCreateBatchReq req);

  ExistenceResp checkExistence(String id, String username, String email);

  UserResp getWithParams(String username, String email);

  UserListResp getByIds(String[] ids);

  void resetPassword(String userId, String newPassword);
}
