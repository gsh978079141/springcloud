package com.gsh.springcloud.account.exception;

public class RoleInUseException extends AccountException {

  public RoleInUseException() {
    super(AccountException.ExceptionEnum.ROLE_IN_USE);
  }
}
