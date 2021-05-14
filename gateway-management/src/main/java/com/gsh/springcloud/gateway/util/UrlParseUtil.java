package com.gsh.springcloud.gateway.util;

import cn.hutool.core.util.StrUtil;
import com.gsh.springcloud.gateway.domain.ScopePermission;
import com.gsh.springcloud.gateway.domain.UrlAndPermission;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * url解析工具
 *
 * @author gsh
 */
public class UrlParseUtil {

  /**
   * 解析url，从给定的权限列表中找到符合的权限
   *
   * @param url
   * @param method
   * @param urlAndPermissions
   * @return
   */
  public static Optional<String[]> getPermissionFromUrl(String url, String method, List<UrlAndPermission> urlAndPermissions) {
    for (UrlAndPermission urlAndPermission : urlAndPermissions) {
      if (StrUtil.isEmpty(urlAndPermission.getUrl())) {
        continue;
      }
      if (!Pattern.matches(urlAndPermission.getUrl(), url)) {
        continue;
      }
      for (ScopePermission scopePermission : urlAndPermission.getScopePermission()) {
        if (method.equalsIgnoreCase(scopePermission.getScope())) {
          return Optional.of(scopePermission.getPermissions());
        }
      }
    }
    return Optional.empty();
  }

}
