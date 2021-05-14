package com.gsh.springcloud.account.keycloak;

import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.dto.RoleDto;
import com.gsh.springcloud.account.request.RoleCreateReq;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;

import java.util.List;
import java.util.Set;

/**
 * 封装keycloak的一系列操作
 *
 * @author jun
 */
public interface KeycloakHandlerService {


  /**
   * 获取当前realm下指定client数据
   *
   * @param clientId
   * @return
   */
  ClientAuthorizationDataDto getClientDataOfCurRealm(String clientId);

  /**
   * 获取指定realm下指定client数据
   *
   * @param clientId
   * @return
   */
  ClientAuthorizationDataDto getClientData(String realm, String clientId);

  /**
   * 导出指定client下，权限数据（角色限定）
   *
   * @param realm
   * @param clientId
   * @param roles
   * @return
   */
  ClientAuthorizationDataDto getClientData(String realm, String clientId, Set<String> roles);

  /**
   * 导入权限数据
   *
   * @param data
   */
  void importClientAuthorizationData(ClientAuthorizationDataDto data);

  /**
   * 获取用户在指定client下的角色列表
   *
   * @param clientId
   * @param userId
   * @return
   */
  List<RoleDto> listRolesOfUser(String clientId, String userId);

  /**
   * 新建角色以及策略
   *
   * @param clientId,req
   * @return
   */
  void createRoleAndRolePolicy4Client(String clientId, RoleCreateReq req);


  /**
   * 查询 Policy
   *
   * @param
   * @return
   */
  Set<String> getExistingPolicies(String clientId);

  /**
   * update role
   *
   * @param clientId,dto
   * @return
   */
  void updateRole(String clientId, RoleDto dto);

  /**
   * delete role
   *
   * @param clientId,rr
   * @return
   */
  void deleteRole(String clientId, RoleDto rr);

  /**
   * binding role&permisson
   *
   * @param clientId,role,permissionDtos,permissionIds
   * @return permisson nameList
   */
  List<String> bindingRoleAndPermissions(String clientId, RoleDto role, List<PermissionDto> permissions, List<String> permissionIds);

  /**
   * @param clientId,roleId
   * @return
   */
  RoleDto getRoleById(String clientId, String roleId);


  /**
   * unbinding role&permissions
   *
   * @param clientId,role,permissionDtos,permissionIds
   * @return
   */
  void unbindingRoleAndPermissions(String clientId, RoleDto role, List<PermissionDto> permissions, List<String> permissionIds);

  /**
   * binding role&permisson
   *
   * @param clientId
   * @param role
   * @param permissions
   * @return
   */
  List<String> bindingRoleAndPermissions(String clientId, RoleDto role, List<ScopePermissionRepresentation> permissions);

  /**
   * unbinding role&permissions
   *
   * @param clientId
   * @param roleDto
   * @param unbindPermissionRepresentations
   */
  void unbindingRoleAndPermissions(String clientId, RoleDto roleDto, List<ScopePermissionRepresentation> unbindPermissionRepresentations);

  /**
   * @param clientId ,roleName
   * @return
   */
  boolean checkRoleInUse(String clientId, String roleName);

  /**
   * @param clientId,roleId
   * @return
   */
  RoleDto getRoleByName(String clientId, String roleName);


}
