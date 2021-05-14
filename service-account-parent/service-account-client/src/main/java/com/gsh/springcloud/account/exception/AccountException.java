package com.gsh.springcloud.account.exception;

import com.gsh.springcloud.common.exception.AbstractBaseException;
import io.swagger.annotations.ApiModel;

@ApiModel("账号服务基础异常")
public class AccountException extends AbstractBaseException {

  public AccountException(AccountException.ExceptionEnum exp) {
    super(exp.getCode(), exp.getStatus());
  }

  public enum ExceptionEnum {
    REALM_NOT_FOUND(500, "REALM_NOT_FOUND"),
    USER_NOT_FOUND(500, "USER_NOT_FOUND"),
    PASSWORD_ERROR(500, "PASSWORD_ERROR"),
    USER_CREATE_FAILED(400, "USER_CREATE_FAILED"),
    USER_ALREADY_EXIST(500, "USER_ALREADY_EXIST"),
    CLIENT_NOT_FOUND(500, "CLIENT_NOT_FOUND"),
    ROLE_NOT_FOUND(500, "ROLE_NOT_FOUND"),
    ROLE_NAMES_INVALID(500, "ROLE_NAMES_INVALID"),
    CREATE_PERMISSION_ERROR(500, "CREATE_PERMISSION_ERROR"),
    CREATE_RESOURCE_ERROR(500, "CREATE_RESOURCE_ERROR"),
    CREATE_ROLE_ERROR(500, "CREATE_ROLE_ERROR"),
    CREATE_POLICY_ERROR(500, "CREATE_POLICY_ERROR"),
    ROLE_IN_USE(500, "ROLE_IN_USE"),
    ROLE_NAME_IS_LOCKED(500, "ROLE_NAME_IS_LOCKED");

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
