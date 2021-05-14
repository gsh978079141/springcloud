package com.gsh.springcloud.gateway.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("测试url解析")
public class TestUrlParseReq {

  @NotBlank(message = "method is required!")
  private String method;

  @NotBlank(message = "url is required!")
  private String url;
}
