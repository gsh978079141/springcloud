//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel("意料之外的异常")
public class UnexpectedException extends AbstractBaseException {
  public UnexpectedException() {
    super(CommonErrorEnum.INTERNAL_SERVER_ERROR.getCode(), CommonErrorEnum.INTERNAL_SERVER_ERROR.getStatus());
  }
}
