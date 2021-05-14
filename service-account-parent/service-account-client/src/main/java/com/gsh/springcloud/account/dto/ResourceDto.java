package com.gsh.springcloud.account.dto;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gsh
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto implements Serializable {

  private String id;

  private String name;

  private Set<String> uris = Sets.newHashSet();

  private Set<String> scopes = Sets.newHashSet();

  private String type;

  private Boolean ownerManagedAccess;

  private String displayName;

  private Map<String, List<String>> attributes = Maps.newHashMap();

}
