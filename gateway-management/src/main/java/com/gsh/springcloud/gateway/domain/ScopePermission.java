package com.gsh.springcloud.gateway.domain;

import lombok.Data;

/**
 * @author maj
 */
@Data
public class ScopePermission {
  private String scope;
  private String[] permissions;
}
