package com.gsh.springcloud.gateway.keycloak;

import com.gsh.springcloud.gateway.domain.ClientAuthorizationDataDto;

import java.util.Set;

/**
 * @author gsh
 */
public interface AuthDataCache {


  void refresh(ClientAuthorizationDataDto data);


  boolean hasPermissionInRoles(String[] permissions, Set<String> roles);

  /**
   * 向redis中存储角色与权限关系
   *
   * @param roleName
   * @param permissions
   */
  void saveRole(String roleName, Set<String> permissions);

  /**
   * redis中清除角色与权限对应关系
   *
   * @param roleName
   */
  void deleteRole(String roleName);

}
