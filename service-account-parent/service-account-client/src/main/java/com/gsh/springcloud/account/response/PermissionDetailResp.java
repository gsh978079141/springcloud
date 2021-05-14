package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.dto.RoleDetailDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class PermissionDetailResp extends PermissionResp {

  private List<RoleDetailDto> roles = Lists.newArrayList();

  private List<ResourceDto> resources = Lists.newArrayList();

  public PermissionDetailResp() {
  }

  public PermissionDetailResp(PermissionDto resp) {
    BeanUtils.copyProperties(resp, this);
  }
}
