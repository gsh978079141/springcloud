package com.gsh.springcloud.account.request;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class UserRolesBindReq {

  @NotEmpty
  private List<String> roles = Lists.newArrayList();

  @Tolerate
  public UserRolesBindReq() {
  }
}
