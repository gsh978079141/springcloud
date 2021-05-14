package com.gsh.springcloud.account.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gsh.springcloud.common.validation.UpdateValidate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * @author wal
 * @date 2021/4/16 10:43
 */
@Data
@ApiModel("角色新增/更新信息")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RoleUpdateDto {
  @NotBlank(groups = UpdateValidate.class, message = "角色ID不能为空")
  @Size(min = 1, max = 36, message = "角色id长度不能超过36")
  @ApiModelProperty(value = "角色ID")
  private String id;

  @NotBlank(message = "客户端ID不能为空")
  @ApiModelProperty(value = "客户端ID")
  private String clientId;

  @NotBlank(message = "角色名称不能为空")
  @ApiModelProperty("name of role")
  private String name;

  @ApiModelProperty("description of role")
  private String description;

  @ApiModelProperty("whether it's a client role")
  private Boolean clientRole = false;

  @ApiModelProperty("extends parameter key-values")
  private Map<String, List<String>> attributes = Maps.newHashMap();

  @ApiModelProperty("permissionIds")
  private List<String> permissionIds = Lists.newArrayList();
}
