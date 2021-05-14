package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.PermissionDto;
import lombok.Data;

import java.util.List;

@Data
public class ResourceDetailResp extends ResourceResp {

  private List<PermissionDto> associatedPermissions = Lists.newArrayList();

  public ResourceDetailResp() {
  }

}
