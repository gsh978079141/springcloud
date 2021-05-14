package com.gsh.springcloud.account.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author jun
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDetailDto extends ResourceDto {

  private List<PermissionDto> associatedPermissions = Lists.newArrayList();

}
