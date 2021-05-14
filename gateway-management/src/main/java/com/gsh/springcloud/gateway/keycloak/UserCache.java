package com.gsh.springcloud.gateway.keycloak;

import com.gsh.springcloud.gateway.domain.UserDto;
import com.sun.istack.Nullable;


/**
 * 用户数据缓存
 *
 * @author gsh
 */
public interface UserCache {

  /**
   * 用户信息缓存过期时间
   * 固定为1小时
   */
  int EXPIRE_SECONDS = 60 * 60;


  @Nullable
  UserDto get(String username);

  void put(UserDto user);
}
