package com.gsh.springcloud.gateway.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gsh
 */
@Data
@ApiModel(description = "client权限认证数据")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthorizationDataDto {

  @ApiModelProperty("权限列表")
  private List<PermissionDto> permissions;

  @ApiModelProperty("角色列表")
  private List<RoleDto> roles;

  @ApiModelProperty("角色的权限集合Map(角色名为key)")
  private Map<String, Set<String>> rolePermissionsMap;


}
