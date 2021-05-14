package com.gsh.springcloud.account.request;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRoleBindReq {

  private List<String> roleNames = Lists.newArrayList();

  private List<String> clientRoleNames = Lists.newArrayList();
}
