package com.gsh.springcloud.gateway.exception;

import com.google.common.collect.Lists;

import java.util.List;

public class RoleNotFoundException extends AccountException {
  public RoleNotFoundException(String roleName) {
    super(ExceptionEnum.ROLE_NOT_FOUND);
    setArguments(Lists.newArrayList(roleName).toArray());
  }

  public RoleNotFoundException(List<String> roleName) {
    super(ExceptionEnum.ROLE_NOT_FOUND);
    setArguments(roleName.toArray());
  }
}
