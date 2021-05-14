package com.gsh.springcloud.account.response;


import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.PermissionDetailDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class PermissionDetailListResp extends BaseResponse {

  private List<PermissionDetailDto> list = Lists.newArrayList();

  public PermissionDetailListResp(List<PermissionDetailDto> list) {
    this.list = list;
  }

  public PermissionDetailListResp() {
  }
}
