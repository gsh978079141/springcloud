//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel(
        value = "文件上传错误",
        description = "上传文件时出现错误"
)
public class UploadFileErrorException extends AbstractBaseException {
  public UploadFileErrorException() {
    super(CommonErrorEnum.UPLOAD_FILE_ERROR.getCode(), CommonErrorEnum.UPLOAD_FILE_ERROR.getStatus());
  }

  public UploadFileErrorException(Exception e) {
    super(CommonErrorEnum.UPLOAD_FILE_ERROR.getCode(), CommonErrorEnum.UPLOAD_FILE_ERROR.getStatus(), e);
  }
}
