package com.gsh.springcloud.common.exception;

/**
 * @author gsh
 */
public class DataValidateException extends AbstractBaseException {


  public DataValidateException(String message) {
    super(CommonExceptionEnum.DATA_VALIDATION_ERROR.getCode(),
            CommonExceptionEnum.DATA_VALIDATION_ERROR.getStatus(),
            message);
  }
}
