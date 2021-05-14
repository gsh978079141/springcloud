package com.gsh.springcloud.account.request;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author jun
 */
@Data
@ApiModel(description = "导出的权限数据请求")
public class ExportAuthorizationReq {

  @NotBlank
  private String realmName;

  @NotBlank
  private String clientId;

  @ApiModelProperty(value = "指定的角色列表")
  private Set<String> targetRoles = Sets.newHashSet();

}
