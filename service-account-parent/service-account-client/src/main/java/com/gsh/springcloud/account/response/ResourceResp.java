package com.gsh.springcloud.account.response;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class ResourceResp extends BaseResponse {

  private String id;

  private String name;

  private Set<String> uris = Sets.newHashSet();

  private String type;

  private Boolean ownerManagedAccess;

  private String displayName;

  private Map<String, List<String>> attributes = Maps.newHashMap();
}
