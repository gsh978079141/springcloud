package com.gsh.springcloud.user.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户表(User)表DTO类
 *
 * @author EasyCode
 */
@ApiModel(description = "用户表")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @ApiModelProperty(value = "ID")
  private Long id;

  @ApiModelProperty(value = "用户名")
  private String userName;

  @ApiModelProperty(value = "密码")
  private String password;

  @ApiModelProperty(value = "年龄")
  private Integer age;

  @ApiModelProperty(value = "积分")
  private Double integral;

}