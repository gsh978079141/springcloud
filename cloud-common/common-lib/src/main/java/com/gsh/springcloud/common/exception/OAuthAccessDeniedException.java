//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel("无权访问该资源")
public class OAuthAccessDeniedException extends AbstractBaseException {
  public OAuthAccessDeniedException() {
    super(CommonErrorEnum.ACCESS_DENIED.getCode(), CommonErrorEnum.ACCESS_DENIED.getStatus());
  }

  public OAuthAccessDeniedException(String detail) {
    super(CommonErrorEnum.ACCESS_DENIED.getCode(), CommonErrorEnum.ACCESS_DENIED.getStatus());
    this.setDetail(detail);
  }
}
