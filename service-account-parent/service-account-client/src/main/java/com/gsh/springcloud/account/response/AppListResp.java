package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class AppListResp extends BaseResponse {

  private List<AppResp> list = Lists.newArrayList();

  public AppListResp(List<AppResp> list) {
    this.list = list;
  }

  public AppListResp() {
  }
}
