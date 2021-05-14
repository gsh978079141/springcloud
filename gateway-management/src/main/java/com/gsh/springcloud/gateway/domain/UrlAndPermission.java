package com.gsh.springcloud.gateway.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maj
 */
@Data
public class UrlAndPermission {
  private String url;
  private List<ScopePermission> scopePermission = new ArrayList<>();
}
