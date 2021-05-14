package com.gsh.springcloud.gateway.exception;

import com.google.common.collect.Lists;

public class UserAlreadyExistException extends AccountException {

  public UserAlreadyExistException(String username) {
    super(ExceptionEnum.USER_ALREADY_EXIST);
    setArguments(Lists.newArrayList(username).toArray());
  }

  public UserAlreadyExistException(String username, String email) {
    super(ExceptionEnum.USER_ALREADY_EXIST);
    setArguments(Lists.newArrayList(username, email).toArray());
  }

}
