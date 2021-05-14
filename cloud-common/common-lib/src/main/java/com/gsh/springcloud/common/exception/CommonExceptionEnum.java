package com.gsh.springcloud.common.exception;

import org.apache.http.HttpStatus;

/**
 * @author gsh
 */

public enum CommonExceptionEnum {

  /**
   * 错误的请求
   */
  BAD_REQUEST(HttpStatus.SC_BAD_REQUEST, "BAD_REQUEST"),

  /**
   * 请求数据错误，未通过校验
   */
  DATA_VALIDATION_ERROR(HttpStatus.SC_BAD_REQUEST, "DATA_VALIDATION_ERROR"),

  UNSUPPORTED_MEDIA_TYPE(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED_MEDIA_TYPE"),

  /**
   * 无效的授权
   */
  INVALID_ACCESS_TOKEN(HttpStatus.SC_UNAUTHORIZED, "UNAUTHORIZED"),
  /**
   * 未授权
   */
  ACCESS_DENIED(HttpStatus.SC_FORBIDDEN, "FORBIDDEN"),
  /**
   * 资源不存在
   */
  NOT_FOUND(HttpStatus.SC_NOT_FOUND, "NOT_FOUND"),

//  UPLOAD_FILE_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR, "UPLOAD_FILE_ERROR"),
//  DELETE_FILE_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_ERROR"),

  /**
   * 服务器内部异常
   * 服务端未捕获异常时的默认返回异常
   */
  INTERNAL_SERVER_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),

  /**
   * 服务不可用
   */
  SERVICE_UNAVAILABLE(HttpStatus.SC_SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE");


  private int status;
  private String code;

  CommonExceptionEnum(int status, String code) {
    this.status = status;
    this.code = code;
  }

  public int getStatus() {
    return this.status;
  }

  public String getCode() {
    return this.code;
  }

}
