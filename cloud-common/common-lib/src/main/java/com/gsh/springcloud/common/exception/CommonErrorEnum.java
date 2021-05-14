//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

public enum CommonErrorEnum {
  BAD_REQUEST(400, "BAD_REQUEST"),
  INVALID_ACCESS_TOKEN(401, "INVALID_ACCESS_TOKEN"),
  ACCESS_DENIED(403, "ACCESS_DENIED"),
  UPLOAD_FILE_ERROR(500, "UPLOAD_FILE_ERROR"),
  DELETE_FILE_ERROR(500, "DELETE_FILE_ERROR"),
  INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
  SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),
  DATA_VALIDATION_ERROR(415, "DATA_VALIDATION_ERROR");

  private int status;
  private String code;

  private CommonErrorEnum(int status, String code) {
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
