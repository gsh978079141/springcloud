package com.gsh.springcloud.account.request;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionBindReq {

  private String description;

  @NotEmpty
  private List<String> permissionIds = Lists.newArrayList();
}
