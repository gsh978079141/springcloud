package com.gsh.springcloud.account.dto;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
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

  @Deprecated
  private Map<String, List<String>> attributesMap = Maps.newHashMap();

}
