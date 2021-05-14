package com.gsh.springcloud.gateway.exception;

public class UserNotFoundException extends AccountException {

  public UserNotFoundException() {
    super(ExceptionEnum.USER_NOT_FOUND);
  }
}
