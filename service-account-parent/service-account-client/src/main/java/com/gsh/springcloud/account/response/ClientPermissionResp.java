package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class ClientPermissionResp extends BaseResponse {

  private String clientId;

  private String clientName;

  private List<PermissionDto> permissions = Lists.newArrayList();

  public ClientPermissionResp(String clientId, String clientName, List<PermissionDto> permissions) {
    this.clientId = clientId;
    this.clientName = clientName;
    this.permissions = permissions;
  }

  public ClientPermissionResp() {
  }
}
