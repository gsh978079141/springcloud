package com.gsh.springcloud.gateway.exception;

public class RoleInUseException extends AccountException {

  public RoleInUseException() {
    super(ExceptionEnum.ROLE_IN_USE);
  }
}
