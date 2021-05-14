package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.request.PermissionRoleBindReq;
import com.gsh.springcloud.account.response.PermissionListResp;
import com.gsh.springcloud.common.vo.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "${feign.url.service-account}/permissions", name = "permissionClient")
@Api(tags = "Permissions API", value = "permission")
public interface PermissionClient {

  @GetMapping("permissions")
  JsonResult<List<PermissionDto>> listPermissions(@RequestParam(value = "clientId", required = true) String clientId);


  @GetMapping("users/{user-id}/clients/{client-id}")
  PermissionListResp listPermissionsOfUser(@PathVariable("user-id") String userId, @PathVariable("client-id") String clientId);

  @GetMapping("roles/{role-id}/clients/{client-id}")
  PermissionListResp listPermissionsOfRole(@PathVariable("role-id") String roleId, @PathVariable("client-id") String clientId);

  @PutMapping("{permission-id}/role-binds/clients/{client-id}")
  void bindRoles(@PathVariable("client-id") String clientId, @PathVariable("permission-id") String permissionId, @RequestBody PermissionRoleBindReq req);

  @PutMapping("{permission-id}/role-unbinds/clients/{client-id}")
  void unbindRoles(@PathVariable("client-id") String clientId, @PathVariable("permission-id") String permissionId, @RequestBody PermissionRoleBindReq req);


  @GetMapping("roles/{role-id}/clients/{client-id}/init")
  PermissionListResp listPermissionsOfRoleByInit(@PathVariable("role-id") String roleId, @PathVariable("client-id") String clientId);

}
