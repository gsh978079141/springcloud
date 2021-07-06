package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.dto.UserDetailDto;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserListResp extends BaseResponse {

    private List<UserDetailDto> list = Lists.newArrayList();

  public UserListResp() {
  }

    public UserListResp(List<UserDetailDto> list) {
        this.list = list;
    }
}
