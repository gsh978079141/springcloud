package com.gsh.springcloud.gateway.domain;

import lombok.Data;

import java.util.Set;

/**
 * @author maj
 */
@Data
public class RoleAndPermission {
  String roleName;
  Set<String> permissions;
}
