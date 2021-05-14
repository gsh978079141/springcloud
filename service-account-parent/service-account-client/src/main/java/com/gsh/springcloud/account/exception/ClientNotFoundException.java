package com.gsh.springcloud.account.exception;

import com.google.common.collect.Lists;

public class ClientNotFoundException extends AccountException {

  public ClientNotFoundException(String clientId) {
    super(ExceptionEnum.CLIENT_NOT_FOUND);
    setArguments(Lists.newArrayList(clientId).toArray());
  }
}
