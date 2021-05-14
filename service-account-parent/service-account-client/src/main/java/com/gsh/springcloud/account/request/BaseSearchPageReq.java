package com.gsh.springcloud.account.request;

import com.gsh.springcloud.account.support.ObjectRequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public abstract class BaseSearchPageReq {

  @Min(1)
  @NotNull
  @ObjectRequestParameter("page_num")
  @ApiModelProperty(name = "page_num", value = "1")
  private Integer pageNum = 1;

  @Range(min = 5, max = 100)
  @NotNull
  @ObjectRequestParameter("page_size")
  @ApiModelProperty(name = "page_size", value = "10")
  private Integer pageSize = 10;

  public int calculateStartRow() {
    return (pageNum - 1) * pageSize;
  }
}
