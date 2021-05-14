package com.gsh.springcloud.account.response;

import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

@Data
public class PermissionResp extends BaseResponse {

  private String id;

  private String name;

  private String description;

  private String type;

  private String decisionStrategy;

  private String logic;


}
