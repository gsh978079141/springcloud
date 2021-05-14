package com.gsh.springcloud.account.response;

import com.gsh.springcloud.common.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色是否使用中")
public class RoleInUseResp extends BaseResponse {


  @ApiModelProperty("是否使用中")
  private boolean inUse;

  @ApiModelProperty("角色名称")
  private String roleName;

  public RoleInUseResp(boolean inUse, String roleName) {
    this.inUse = inUse;
    this.roleName = roleName;
  }

  public RoleInUseResp() {
  }
}
