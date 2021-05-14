package com.gsh.springcloud.gateway.exception;

public class PasswordErrorException extends AccountException {

  public PasswordErrorException() {
    super(ExceptionEnum.PASSWORD_ERROR);
  }
}
