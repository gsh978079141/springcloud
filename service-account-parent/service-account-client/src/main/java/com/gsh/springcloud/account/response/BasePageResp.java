package com.gsh.springcloud.account.response;

import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

@Data
public class BasePageResp extends BaseResponse {

  private Integer pageNum = 1;

  private Integer pageSize = 10;

  private Integer total;

  private Integer pageTotal;


}
