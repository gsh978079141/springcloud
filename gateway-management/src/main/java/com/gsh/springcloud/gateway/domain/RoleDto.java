package com.gsh.springcloud.gateway.domain;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@ApiModel(description = "角色")
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

  private String id;

  private String name;

  private Boolean clientRole;

  private String clientId;

  private String description;

  @Builder.Default
  private Map<String, List<String>> attributes = Maps.newHashMap();


}
