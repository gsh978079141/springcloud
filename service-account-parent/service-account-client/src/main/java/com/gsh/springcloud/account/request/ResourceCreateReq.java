package com.gsh.springcloud.account.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCreateReq {

  private String name;

  private Set<String> uris;

  private String type = "api-access";

  private Set<String> scopes;

  private String iconUri;

  private Boolean ownerManagedAccess = false;

  private String displayName;

  private Map<String, List<String>> attributes;


}
