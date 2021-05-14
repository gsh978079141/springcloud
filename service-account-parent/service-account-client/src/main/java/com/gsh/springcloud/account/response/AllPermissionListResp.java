package com.gsh.springcloud.account.response;


import com.google.common.collect.Lists;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class AllPermissionListResp extends BaseResponse {

  private List<ClientPermissionResp> clientPermissions = Lists.newArrayList();

  public AllPermissionListResp(List<ClientPermissionResp> clientPermissions) {
    this.clientPermissions = clientPermissions;
  }

  public AllPermissionListResp() {
  }
}
