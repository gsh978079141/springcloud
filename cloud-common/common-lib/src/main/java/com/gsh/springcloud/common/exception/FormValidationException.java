//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel("表单验证异常")
public class FormValidationException extends AbstractBaseException {
  @ApiModelProperty("异常详情")
  private Map<String, String> data;

  public FormValidationException() {
    super(CommonErrorEnum.DATA_VALIDATION_ERROR.getCode(), CommonErrorEnum.DATA_VALIDATION_ERROR.getStatus());
  }

  public FormValidationException(Map<String, String> data) {
    super(CommonErrorEnum.DATA_VALIDATION_ERROR.getCode(), CommonErrorEnum.DATA_VALIDATION_ERROR.getStatus());
    this.setData(data);
  }

  public Map<String, String> getData() {
    return this.data;
  }

  public void setData(Map<String, String> data) {
    this.data = data;
  }
}
