package com.gsh.springcloud.gateway.exception;

import com.gsh.springcloud.common.exception.AbstractBaseException;
import io.swagger.annotations.ApiModel;

@ApiModel("账号服务基础异常")
public class AccountException extends AbstractBaseException {

  public AccountException(ExceptionEnum exp) {
    super(exp.getCode(), exp.getStatus());
  }


  enum ExceptionEnum {
    USER_NOT_FOUND(500, "USER_NOT_FOUND"),
    PASSWORD_ERROR(400, "PASSWORD_ERROR"),
    USER_CREATE_FAILED(500, "USER_CREATE_FAILED"),
    USER_ALREADY_EXIST(500, "USER_ALREADY_EXIST"),
    REALM_NOT_FOUND(500, "REALM_NOT_FOUND"),
    CLIENT_NOT_FOUND(500, "CLIENT_NOT_FOUND"),
    ROLE_NOT_FOUND(500, "ROLE_NOT_FOUND"),
    ROLE_NAMES_INVALID(500, "ROLE_NAMES_INVALID"),
    ROLE_IN_USE(500, "ROLE_IN_USE");

    private String code;
    private int status;

    ExceptionEnum(int status, String code) {
      this.code = code;
      this.status = status;
    }

    public String getCode() {
      return code;
    }

    public int getStatus() {
      return status;
    }
  }
}
