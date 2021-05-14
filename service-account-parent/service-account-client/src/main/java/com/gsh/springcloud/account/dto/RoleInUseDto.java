package com.gsh.springcloud.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author wal
 * @date 2021/4/16 11:12
 */
@ApiModel("使用中用户返回")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleInUseDto {
  @ApiModelProperty("是否使用中")
  private boolean inUse;

  @ApiModelProperty("角色名称")
  private String name;
}
