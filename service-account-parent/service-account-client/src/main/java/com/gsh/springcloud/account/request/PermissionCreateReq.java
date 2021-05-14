package com.gsh.springcloud.account.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCreateReq {

  private String name;

  private String description;

  private String type = "scope";

  private String logic = "POSITIVE";

  private String decisionStrategy = "AFFIRMATIVE";

  private Set<String> scopes;

  private Set<String> policies;

  private Set<String> resources;


}
