package com.gsh.springcloud.gateway.service;

import com.gsh.springcloud.gateway.domain.UserDto;
import com.gsh.springcloud.gateway.domain.UserPrincipal;

import java.util.Optional;
import java.util.Set;

/**
 * 用户权限认证相关service
 *
 * @author gsh
 */
public interface AuthService {

  Optional<UserPrincipal> verifyToken(String token);

  Optional<UserDto> findUser(String username);

  boolean hasPermissionInRoles(String[] permissions, Set<String> roles);

}
