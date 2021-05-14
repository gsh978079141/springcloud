package com.gsh.springcloud.account.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class UserPageResp extends BasePageResp {

  private List<UserResp> list = Lists.newArrayList();

}
