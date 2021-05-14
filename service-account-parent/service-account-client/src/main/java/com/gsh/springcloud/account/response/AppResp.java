package com.gsh.springcloud.account.response;

import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

@Data

public class AppResp extends BaseResponse {

  private String id;

  private String clientId;

  private String name;

  private String description;

  private String rootUrl;

  private String adminUrl;

  private String baseUrl;

  private Boolean surrogateAuthRequired;
}
