package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.UserDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserListResp extends BaseResponse {

  private List<UserDto> list = Lists.newArrayList();

  public UserListResp() {
  }

  public UserListResp(List<UserDto> list) {
    this.list = list;
  }
}
