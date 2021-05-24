package com.gsh.springcloud.gateway.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author gsh
 */
@Slf4j
public class WebFluxRequestUtil {
  public static final String UNKNOWN = "unknown";

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