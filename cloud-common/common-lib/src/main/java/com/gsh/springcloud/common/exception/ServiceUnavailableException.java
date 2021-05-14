//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel("目标服务不可用")
public class ServiceUnavailableException extends AbstractBaseException {
  public ServiceUnavailableException() {
    super(CommonErrorEnum.SERVICE_UNAVAILABLE.getCode(), CommonErrorEnum.SERVICE_UNAVAILABLE.getStatus());
  }
}
