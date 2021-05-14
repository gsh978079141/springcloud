package com.gsh.springcloud.gateway.exception;

import com.google.common.collect.Lists;

public class RealmNotFoundException extends AccountException {

  public RealmNotFoundException(String clientId) {
    super(AccountException.ExceptionEnum.REALM_NOT_FOUND);
    setArguments(Lists.newArrayList(clientId).toArray());
  }
}
