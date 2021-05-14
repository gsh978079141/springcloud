//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsh.springcloud.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
import com.gsh.springcloud.common.utils.ExceptionUtil;
import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("业务异常基础根类")
@JsonSerialize(
        typing = Typing.DYNAMIC
)
@JsonIgnoreProperties({"stackTrace", "localizedMessage", "cause", "suppressed"})
public abstract class AbstractBaseException extends RuntimeException implements ExceptionNotWrappedByHystrix {
  @ApiModelProperty("异常代码")
  private String code;
  @ApiModelProperty("网络状态码")
  private int status;
  @ApiModelProperty("异常消息")
  private String message;
  @ApiModelProperty("是否需要根据异常代码翻译消息")
  @JsonIgnore
  private boolean needTranslation;
  @ApiModelProperty("异常完整类名")
  private String exception;
  @ApiModelProperty("参数集合")
  private Object[] arguments;
  @ApiModelProperty("异常详情")
  private Object detail;
  @ApiModelProperty("时间戳")
  private long timestamp;

  public AbstractBaseException(String code, int status) {
    this.needTranslation = true;
    this.arguments = new Object[0];
    this.timestamp = System.currentTimeMillis();
    this.code = code;
    this.status = status;
    this.message = code;
    this.exception = this.getClass().getName();
  }

  public AbstractBaseException(String code, int status, String message) {
    this.needTranslation = true;
    this.arguments = new Object[0];
    this.timestamp = System.currentTimeMillis();
    this.code = code;
    this.status = status;
    this.message = message;
    this.exception = this.getClass().getName();
  }

  public AbstractBaseException(String code, int status, Exception ex) {
    this(code, status);
    this.detail = ExceptionUtil.getStackTraceInfo(ex);
  }

  public AbstractBaseException(String code, int status, Object... arguments) {
    this(code, status);
    this.arguments = arguments;
  }

  public AbstractBaseException(String code, int status, Exception ex, Object... arguments) {
    this(code, status);
    this.arguments = arguments;
    this.detail = ExceptionUtil.getStackTraceInfo(ex);
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setNeedTranslation(boolean needTranslation) {
    this.needTranslation = needTranslation;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public void setArguments(Object[] arguments) {
    this.arguments = arguments;
  }

  public void setDetail(Object detail) {
    this.detail = detail;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getCode() {
    return this.code;
  }

  public int getStatus() {
    return this.status;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public boolean isNeedTranslation() {
    return this.needTranslation;
  }

  public String getException() {
    return this.exception;
  }

  public Object[] getArguments() {
    return this.arguments;
  }

  public Object getDetail() {
    return this.detail;
  }

  public long getTimestamp() {
    return this.timestamp;
  }
}
