package com.gsh.springcloud.account.request;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleUpdateReq {

  private String name;

  private String description;

  @ApiModelProperty("permissionIds")
  private List<String> permissionIds = Lists.newArrayList();

}
