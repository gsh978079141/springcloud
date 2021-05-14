//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel("无效的访问令牌")
public class InvalidOauthTokenException extends AbstractBaseException {
  public InvalidOauthTokenException() {
    super(CommonErrorEnum.INVALID_ACCESS_TOKEN.getCode(), CommonErrorEnum.INVALID_ACCESS_TOKEN.getStatus());
  }
}
