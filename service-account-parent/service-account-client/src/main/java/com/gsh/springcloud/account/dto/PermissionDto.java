package com.gsh.springcloud.account.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限基础数据
 *
 * @author jun
 */
@Data
@ApiModel(description = "权限基础数据")
public class PermissionDto implements Serializable {

  private String id;

  private String name;

  private String description;

  private String type;

  private String decisionStrategy;

  private String logic;


}
