package com.gsh.springcloud.account.exception;

import com.google.common.collect.Lists;

/**
 * @author maj
 */
public class RoleNameLockedException extends AccountException {
  public RoleNameLockedException(String roleName) {
    super(ExceptionEnum.ROLE_NAME_IS_LOCKED);
    setArguments(Lists.newArrayList(roleName).toArray());
  }

}
