package com.gsh.springcloud.account.exception;

import com.google.common.collect.Lists;

public class UserCreateFailedException extends AccountException {

  public UserCreateFailedException(String username) {
    super(ExceptionEnum.USER_CREATE_FAILED);
    setArguments(Lists.newArrayList(username).toArray());
  }

}
