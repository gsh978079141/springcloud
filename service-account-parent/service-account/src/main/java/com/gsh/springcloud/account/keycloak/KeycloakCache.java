package com.gsh.springcloud.account.keycloak;

import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.dto.ResourceDetailDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.dto.RoleDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface KeycloakCache {

  /**
   * 查询client下所有Resource
   *
   * @param clientId
   * @return
   */
  List<ResourceDto> listAllResources(String clientId);

  /**
   * 查询client下所有scope类型的Permission
   *
   * @param clientId
   * @return
   */
  List<PermissionDto> listAllPermissions(String clientId);

  /**
   * 查询client下所有RESOURCE的Permission
   *
   * @param clientId
   * @return
   */
  Map<String, List<PermissionDto>> listAllResourcePermissions(String clientId);


  /**
   * 查询指定角色列表包含的resource数据
   *
   * @param clientId
   * @param roleNames
   * @return
   */
  Map<String, List<ResourceDto>> getResourcesOfRoleMap(String clientId, Set<String> roleNames);

  /**
   * 查询指定角色列表包含的resource和permission数据
   *
   * @param clientId
   * @param roleNames
   * @return
   */
  List<ResourceDetailDto> listResourceDetailsByRoleNames(String clientId, Set<String> roleNames);


  /**
   * 查询client下当前角色中 是否已存在该角色名称列表中的任意一个
   *
   * @param clientId roleNames
   * @return
   */
  boolean containsRoles(String clientId, Set<String> roleNames);


  /**
   * 查询client下缓存指定角色的权限集合
   *
   * @param clientId,roleNames
   * @return
   */
  List<PermissionDto> listPermissionsOfRoles(String clientId, Set<String> roleNames);

  /**
   * 查询client下缓存角色集合中是否包含指定权限
   *
   * @param clientId,roleNames,permission
   * @return
   */
  boolean hasPermissionInRoles(String clientId, Set<String> roleNames, String permission);


  /**
   * 查询client下所有Role
   *
   * @param clientId
   * @return
   */
  List<RoleDto> listAllRoles(String clientId);


  /**
   * 查询 返回client下指定角色name的role
   *
   * @param clientId,roleNames
   * @return
   */
  List<RoleDto> listRolesByNames(String clientId, Set<String> roleNames);

  /**
   * 查询client下所有Role的Resources
   *
   * @param clientId
   * @return
   */
  Map<String, List<ResourceDto>> listAllRoleResources(String clientId);


  /**
   * 查询client下所有Role的Permission
   *
   * @param clientId
   * @return
   */
  Map<String, List<PermissionDto>> listAllRolesPermissions(String clientId);


  /**
   * 更新角色
   *
   * @param clientId,role
   */
  void updateRole(String clientId, RoleDto role);


  /**
   * 更新缓存中权限
   *
   * @param clientId,roleName
   * @param permissions
   */
  void updateRolePermissions(String clientId, String roleName, Set<String> permissions);


  /**
   * 删除角色完成后，删除缓存中 角色以及角色权限
   *
   * @param role
   */
  void deleteRole(String clientId, RoleDto role);
}
