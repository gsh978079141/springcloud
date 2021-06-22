package com.gsh.springcloud.account.dto;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author gsh
 */
@Data
@SuperBuilder
@ApiModel(description = "用户详细信息")
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto extends UserDto {

  @ApiModelProperty("the key is `client_id`, and the values are the role names that you wanna assign 2 this user on corresponded client")
  private Map<String, List<String>> clientRolesMap = Maps.newHashMap();

  @Deprecated
  private Map<String, List<String>> attributesMap = Maps.newHashMap();

}
