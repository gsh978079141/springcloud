package com.gsh.springcloud.gateway.exception;

public class RoleNamesInvalidException extends AccountException {

  public RoleNamesInvalidException() {
    super(ExceptionEnum.ROLE_NAMES_INVALID);
  }
}
