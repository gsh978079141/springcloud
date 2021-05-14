package com.gsh.springcloud.gateway.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 权限基础数据
 *
 * @author gsh
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "权限基础数据")
public class PermissionDto {

  private String id;

  private String name;

  private String description;

  private String type;

  private String decisionStrategy;

  private String logic;


}
