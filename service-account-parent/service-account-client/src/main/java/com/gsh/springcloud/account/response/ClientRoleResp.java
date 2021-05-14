package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.RoleDetailDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;


@Data
public class ClientRoleResp extends BaseResponse {

  private String clientId;

  private String clientName;

  private List<RoleDetailDto> roles = Lists.newArrayList();
}
