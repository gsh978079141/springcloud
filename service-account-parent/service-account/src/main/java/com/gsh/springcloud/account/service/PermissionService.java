package com.gsh.springcloud.account.service;

import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.request.PermissionRoleBindReq;
import com.gsh.springcloud.account.response.PermissionListResp;

import java.util.List;

public interface PermissionService {

  List<PermissionDto> listPermissions(String clientId);

  void bindRoles(String clientId, String permissionId, PermissionRoleBindReq req);

  void unbindRoles(String clientId, String permissionId, PermissionRoleBindReq req);

  PermissionListResp listPermissionsOfUser(String userId, String clientId);

  PermissionListResp listPermissionsOfRole(String roleId, String clientId);

  PermissionListResp listPermissionsOfRoleByInit(String roleId, String clientId);
}
