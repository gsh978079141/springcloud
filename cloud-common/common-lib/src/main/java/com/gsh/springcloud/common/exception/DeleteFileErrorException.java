//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;

@ApiModel(
        value = "文件删除错误",
        description = "删除文件时出现错误"
)
public class DeleteFileErrorException extends AbstractBaseException {
  public DeleteFileErrorException() {
    super(CommonErrorEnum.DELETE_FILE_ERROR.getCode(), CommonErrorEnum.DELETE_FILE_ERROR.getStatus());
  }

  public DeleteFileErrorException(Exception e) {
    super(CommonErrorEnum.DELETE_FILE_ERROR.getCode(), CommonErrorEnum.DELETE_FILE_ERROR.getStatus(), e);
  }
}
