package com.gsh.springcloud.account.exception;

public class PasswordErrorException extends AccountException {

  public PasswordErrorException() {
    super(ExceptionEnum.PASSWORD_ERROR);
  }
}
