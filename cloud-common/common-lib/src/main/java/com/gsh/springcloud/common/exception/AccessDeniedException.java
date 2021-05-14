package com.gsh.springcloud.common.exception;

/**
 * @author gsh
 */
public class AccessDeniedException extends AbstractBaseException {

  public AccessDeniedException() {
    super(CommonExceptionEnum.ACCESS_DENIED.getCode(),
            CommonExceptionEnum.ACCESS_DENIED.getStatus(),
            "Access denied!");
  }

}
