package com.gsh.springcloud.account.request;

import lombok.Data;

@Data
public class AppUpdateReq {

  private String clientId;

  private String name;

  private String description;

  private String rootUrl;

  private String adminUrl;

  private String baseUrl;

  private Boolean surrogateAuthRequired;
}
