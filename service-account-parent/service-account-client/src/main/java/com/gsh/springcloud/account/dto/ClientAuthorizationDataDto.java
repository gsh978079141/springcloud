package com.gsh.springcloud.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author gsh
 */
@Data
@ApiModel(description = "client权限认证数据")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthorizationDataDto {

  @NotBlank(message = "realm不能为空")
  @ApiModelProperty(value = "所属realm名称")
  private String realm;

  @NotBlank(message = "clientId不能为空")
  @ApiModelProperty(value = "client的业务ID")
  private String clientId;

  @ApiModelProperty("资源列表")
  private List<ResourceDto> resources;

  @ApiModelProperty("权限列表")
  private List<PermissionDto> permissions;

  @ApiModelProperty("角色列表")
  private List<RoleDto> roles;

  @ApiModelProperty("资源的权限集合map(资源名为key)")
  private Map<String, List<PermissionDto>> resourcePermissionsMap;

  @ApiModelProperty("角色的权限集合Map(角色名为key)")
  private Map<String, List<PermissionDto>> rolePermissionsMap;


}
