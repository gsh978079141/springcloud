package com.gsh.springcloud.account.response;


import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.util.RoleUtils;
import lombok.Data;

import java.util.List;

@Data
public class RoleDetailResp extends RoleResp {

  private String id;

  private String name;

  private String description;

  private List<PermissionDto> associatedPermissions = Lists.newArrayList();

  public RoleDetailResp() {
  }

  public String getName() {
    return RoleUtils.removeRolePrefix(name);
  }
}
