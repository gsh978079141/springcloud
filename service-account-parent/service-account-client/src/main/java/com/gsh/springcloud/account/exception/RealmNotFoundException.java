package com.gsh.springcloud.account.exception;

import com.google.common.collect.Lists;

public class RealmNotFoundException extends AccountException {

  public RealmNotFoundException(String realm) {
    super(ExceptionEnum.REALM_NOT_FOUND);
    setArguments(Lists.newArrayList(realm).toArray());
  }
}
