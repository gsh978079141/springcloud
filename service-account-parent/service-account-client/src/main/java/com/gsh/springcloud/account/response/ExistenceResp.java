package com.gsh.springcloud.account.response;

import com.gsh.springcloud.common.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@ApiModel("用户存在检查返回")
@SuperBuilder
public class ExistenceResp extends BaseResponse {


  @ApiModelProperty("是否存在")
  private boolean exist;

  @ApiModelProperty("检查字段名称")
  private String fieldName;

  @ApiModelProperty("检查字段值")
  private String value;

  public ExistenceResp(boolean exist) {
    this.exist = exist;
  }

  public ExistenceResp(boolean exist, String fieldName, String value) {
    this.exist = exist;
    this.fieldName = fieldName;
    this.value = value;
  }

  public ExistenceResp() {
  }
}
