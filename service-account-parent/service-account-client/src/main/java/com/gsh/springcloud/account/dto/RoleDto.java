package com.gsh.springcloud.account.dto;

import com.google.common.collect.Maps;
import com.gsh.springcloud.account.constant.RoleConstants;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author jun
 */
@Data
@SuperBuilder
@ApiModel(description = "角色")
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

  private String id;

  private String name;

  private Boolean clientRole;

  private String clientId;

  private String description;

  private Boolean editable;

  @Builder.Default
  private Map<String, List<String>> attributes = Maps.newHashMap();

  public Boolean getEditable() {
    return !RoleConstants.FIXED_ROLE_NAMES.contains(this.name);
  }

}
