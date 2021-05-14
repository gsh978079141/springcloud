package com.gsh.springcloud.starter.web.exception;


import com.gsh.springcloud.common.exception.AbstractBaseException;

/**
 * 内部服务调用异常
 *
 * @author gsh
 */
public class ServiceException extends AbstractBaseException {

  public ServiceException(String code, int status, String message) {
    super(code, status, message);
  }

}
