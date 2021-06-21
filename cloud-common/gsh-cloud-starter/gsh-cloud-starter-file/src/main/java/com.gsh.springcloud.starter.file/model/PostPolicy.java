package com.gsh.springcloud.starter.file.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: gsh
 * @Date: 2019-03-01 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("OSS上传策略")
public class PostPolicy {

  @ApiModelProperty("访问ID")
  String accessId;

  @ApiModelProperty("策略")
  String policy;

  @ApiModelProperty("签名")
  String signature;

  @ApiModelProperty("地址")
  String dir;

  @ApiModelProperty("Host")
  String host;

  @ApiModelProperty("超时时间")
  String expire;
}
