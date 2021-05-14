package com.gsh.springcloud.account.request;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateBatchReq {

  @NotEmpty
  private List<UserCreateReq> users = Lists.newArrayList();
}
