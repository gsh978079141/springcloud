package com.gsh.springcloud.account.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gsh.springcloud.account.constant.RoleConstants;
import com.gsh.springcloud.account.util.RoleUtils;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author wal
 * @date 2021/4/2 15:48
 */
@Data
@SuperBuilder
@ApiModel(description = "角色-权限")
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionsDto {
  private String id;

  private String name;

  private Boolean clientRole;

  private String clientId;

  private String description;

  private Map<String, List<String>> attributes = Maps.newHashMap();

  private List<PermissionDto> associatedPermissions = Lists.newArrayList();

  public boolean isEditable() {
    return !RoleConstants.FIXED_ROLE_NAMES.contains(this.name);
  }

  public String getName() {
    return RoleUtils.removeRolePrefix(name);
  }
}
