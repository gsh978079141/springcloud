package com.gsh.springcloud.account.web;

import com.gsh.springcloud.account.client.PermissionClient;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.request.PermissionRoleBindReq;
import com.gsh.springcloud.account.response.PermissionListResp;
import com.gsh.springcloud.account.service.PermissionService;
import com.gsh.springcloud.common.vo.JsonResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * @author jasonlee
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController implements PermissionClient {

  @Resource
  PermissionService permissionService;

  @Override
  public JsonResult<List<PermissionDto>> listPermissions(String clientId) {
    return JsonResult.success(permissionService.listPermissions(clientId));
  }


  @Override
  public PermissionListResp listPermissionsOfUser(@PathVariable("user-id") String userId, @PathVariable("client-id") String clientId) {
    return permissionService.listPermissionsOfUser(userId, clientId);
  }

  @Override
  public PermissionListResp listPermissionsOfRole(@PathVariable("role-id") String roleId, @PathVariable("client-id") String clientId) {
    return permissionService.listPermissionsOfRole(roleId, clientId);
  }

  @Override
  public void bindRoles(@PathVariable("client-id") String clientId, @PathVariable("permission-id") String permissionId, @RequestBody @Valid PermissionRoleBindReq req) {
    permissionService.bindRoles(clientId, permissionId, req);
  }

  @Override
  public void unbindRoles(@PathVariable("client-id") String clientId, @PathVariable("permission-id") String permissionId, @RequestBody @Valid PermissionRoleBindReq req) {
    permissionService.unbindRoles(clientId, permissionId, req);
  }

  @Override
  public PermissionListResp listPermissionsOfRoleByInit(@PathVariable("role-id") String roleId, @PathVariable("client-id") String clientId) {
    return permissionService.listPermissionsOfRoleByInit(roleId, clientId);
  }


}
