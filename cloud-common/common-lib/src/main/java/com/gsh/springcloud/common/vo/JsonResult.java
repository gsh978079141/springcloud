package com.gsh.springcloud.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author gsh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JsonResult<T> implements Serializable {

  private static final long serialVersionUID = 0L;

  public static final String DEFAULT_ERROR_CODE = "INTERNAL_SERVER_ERROR";

  @Builder.Default
  private Boolean success = true;
  @Builder.Default
  private String code = "SUCCESS";
  @Builder.Default
  private String message = "success";

  @Builder.Default
  private Long time = System.currentTimeMillis();

  private T data;


  public static <T> JsonResult<T> success(T data) {
    JsonResult<T> resp = new JsonResult<>();
    resp.setSuccess(true);
    resp.setData(data);
    return resp;
  }

  public static <T> JsonResult<T> success() {
    JsonResult<T> resp = new JsonResult<>();
    resp.setSuccess(true);
    return resp;
  }


  public static <T> JsonResult<T> failure(String code, String message) {
    JsonResult<T> resp = new JsonResult<>();
    resp.setSuccess(false);
    resp.setCode(code);
    resp.setMessage(message);
    return resp;
  }

  public static <T> JsonResult<T> failureWithCode(String code) {
    JsonResult<T> resp = new JsonResult<>();
    resp.setSuccess(false);
    resp.setCode(code);
    resp.setMessage(code);
    return resp;
  }

  public static <T> JsonResult<T> failureWithMessage(String message) {
    JsonResult<T> resp = new JsonResult<>();
    resp.setSuccess(false);
    resp.setCode(DEFAULT_ERROR_CODE);
    resp.setMessage(message);
    return resp;
  }


  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Boolean getSuccess() {
    return this.success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTime() {
    return this.time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

}
