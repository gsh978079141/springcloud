//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel("Feign请求异常")
public class FeignRequestException extends AbstractBaseException {
  public FeignRequestException() {
    super(CommonErrorEnum.INTERNAL_SERVER_ERROR.getCode(), CommonErrorEnum.INTERNAL_SERVER_ERROR.getStatus());
  }
}
