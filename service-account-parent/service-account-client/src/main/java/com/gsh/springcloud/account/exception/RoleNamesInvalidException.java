package com.gsh.springcloud.account.exception;

public class RoleNamesInvalidException extends AccountException {

  public RoleNamesInvalidException() {
    super(AccountException.ExceptionEnum.ROLE_NAMES_INVALID);
  }
}
