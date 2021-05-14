package com.gsh.springcloud.account.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicieCreateReq {

  private String name;

  private String type = "role";

  private String logic = "POSITIVE";

  private String decisionStrategy = "UNANIMOUS";

  private Set<String> roles;

}
