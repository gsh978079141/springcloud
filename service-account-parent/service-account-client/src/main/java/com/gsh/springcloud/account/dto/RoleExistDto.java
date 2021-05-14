package com.gsh.springcloud.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author wal
 * @date 2021/4/16 9:55
 */
@ApiModel("用户存在返回")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleExistDto {
  @ApiModelProperty("是否存在")
  private boolean exist;

  @ApiModelProperty("检查字段名称")
  private String fieldName;

  @ApiModelProperty("检查字段值")
  private String value;
}
