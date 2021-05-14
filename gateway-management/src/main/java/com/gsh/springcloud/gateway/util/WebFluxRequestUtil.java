package com.gsh.springcloud.gateway.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wh008
 */
@Slf4j
public class WebFluxRequestUtil {
  public static final String UNKNOWN = "unknown";

  private static final String IP_LOCAL = "127.0.0.1";
  private static final String IPV6_LOCAL = "0:0:0:0:0:0:0:1";


  /**
   * 去掉空格,换行和制表符
   *
   * @param str
   * @return
   */
  private static String formatStr(String str) {
    if (str != null && str.length() > 0) {
      Pattern p = Pattern.compile("\\s*|\t|\r|\n");
      Matcher m = p.matcher(str);
      return m.replaceAll("");
    }
    return str;
  }

  public static String getIp(ServerHttpRequest request) {
    Assert.notNull(request, "HttpServletRequest is null");
    HttpHeaders httpHeaders = request.getHeaders();
    String ip = httpHeaders.getFirst("X-Requested-For");
    if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = httpHeaders.getFirst("X-Forwarded-For");
    }
    if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = httpHeaders.getFirst("Proxy-Client-IP");
    }
    if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = httpHeaders.getFirst("WL-Proxy-Client-IP");
    }
    if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = httpHeaders.getFirst("HTTP_CLIENT_IP");
    }
    if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = httpHeaders.getFirst("HTTP_X_FORWARDED_FOR");
    }
    if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = Optional.ofNullable(request.getRemoteAddress())
              .map(address -> address.getAddress().getHostAddress())
              .orElse("");
    }

    return StrUtil.isBlank(ip) ? null : ip.split(",")[0];
  }

}