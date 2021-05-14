package com.gsh.springcloud.account.util;

import java.util.Collection;
import java.util.stream.Collectors;

public class RoleUtils {

  public static String getRoleCompleteName(Long schoolId, String roleName) {
    return schoolId.toString().concat("_").concat(roleName);
  }

  public static String getRoleNamePrefix(Long schoolId) {
    return schoolId.toString().concat("_");
  }

  public static String removeRolePrefix(String roleName) {
    return roleName.substring(roleName.lastIndexOf('_') + 1, roleName.length());
  }

  public static Collection<String> getRoleCompleteNames(Long schoolId, Collection<String> roleNames) {
    return roleNames.stream().map(item -> getRoleCompleteName(schoolId, item)).collect(Collectors.toList());
  }

  public static String removeRolesPrefix(String roleNamesStr) {
    String[] roleNames = roleNamesStr.split(",");
    StringBuilder realRoleNamesStr = new StringBuilder();
    int i = 1;
    for (String roleName : roleNames) {
      realRoleNamesStr.append(removeRolePrefix(roleName));
      if (i < roleNames.length) {
        realRoleNamesStr.append(",");
      }
      i++;
    }
    return realRoleNamesStr.toString();
  }
}
