package com.gsh.springcloud.gateway.keycloak;


import com.gsh.springcloud.gateway.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 封装keycloak的一系列操作
 *
 * @author gsh
 */
public interface KeycloakHandlerService {

  /**
   * 获取当前client数据
   *
   * @return
   */
  ClientAuthorizationDataDto getClientData();

  List<RoleDto> listAllRoles();

  /**
   * 获取指定角色集合和权限集合的映射关系
   *
   * @return
   */
  Map<String, Set<String>> getRolePermissionsMap(List<RoleDto> roles, List<PermissionDto> permissions);


  /**
   * 验证bear token，验证成功则返回token
   *
   * @param token
   * @return
   */
  Optional<UserPrincipal> verifyToken(String token);

  /**
   * 根据用户名查找用户
   *
   * @param username
   * @return
   */
  Optional<UserDto> findUser(String username);


}
