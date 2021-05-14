package com.gsh.springcloud.account.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author gsh
 */
@Data
@ApiModel(description = "权限详情信息")
public class PermissionDetailDto extends PermissionDto {

  /**
   * 所属resource
   */
  private List<ResourceDto> resources = Lists.newArrayList();

}
