package com.gsh.springcloud.gateway.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author flyhorse
 * @since 2019-05-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLog对象", description = "系统日志表")
public class SysLog {

  private static final long serialVersionUID = 1L;


  /**
   * 创建时间
   */
  @ApiModelProperty(value = "日志保存时间")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /**
   * 操作人
   */
  @ApiModelProperty(value = "操作人")
  private String userName;

  /**
   * 操作IP地址
   */
  @ApiModelProperty(value = "操作IP地址")
  private String remoteAddr;

  /**
   * 用户代理
   */
  @ApiModelProperty(value = "用户代理")
  private String userAgent;

  /**
   * 请求URI
   */
  @ApiModelProperty(value = "请求URI")
  private String reqUri;

  /**
   * 请求操作方式POST GET
   */
  @ApiModelProperty(value = "请求操作方式POST GET")
  private String reqMethod;

  /**
   * 请求参数
   */
  @ApiModelProperty(value = "请求参数")
  private String reqParams;

  /**
   * 请求体
   */
  @ApiModelProperty(value = "请求体")
  private String reqBody;

  /**
   * 执行时间
   */
  @ApiModelProperty(value = "请求耗时")
  private Long exeTime;

  /**
   * 异常信息
   */
  @ApiModelProperty(value = "异常信息")
  private String exceptionMsg;


  /**
   * 消息类型
   */
  @ApiModelProperty(value = "消息类型")
  private String contentType;

}