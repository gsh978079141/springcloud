package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.RoleDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class RoleListResp extends BaseResponse {

  private List<RoleDto> list = Lists.newArrayList();

  public RoleListResp(List<RoleDto> list) {
    this.list = list;
  }

  public RoleListResp() {
  }

}
