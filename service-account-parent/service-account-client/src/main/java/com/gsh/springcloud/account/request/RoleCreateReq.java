package com.gsh.springcloud.account.request;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("create role request")
@Builder
public class RoleCreateReq {

  @NotBlank
  @ApiModelProperty("name of role")
  private String name;

  @ApiModelProperty("description of role")
  private String description;

  @ApiModelProperty("whether it's a client role")
  private Boolean clientRole = false;

  @ApiModelProperty("extends parameter key-values")
  private Map<String, List<String>> attributes = Maps.newHashMap();

  @ApiModelProperty("permissionIds")
  private List<String> permissionIds = Lists.newArrayList();

  @Tolerate
  public RoleCreateReq() {
  }
}
