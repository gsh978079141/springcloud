package com.gsh.springcloud.common.utils;

import cn.hutool.core.net.NetUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Optional;

public class HttpRequestUtil {


  private static final String UNKNOWN = "unknown";


  public static String getClientIP(ServerHttpRequest request) {
    String[] headerNames = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
    String ip;
    for (String header : headerNames) {
      ip = request.getHeaders().getFirst(header);
      if (!NetUtil.isUnknown(ip)) {
        return NetUtil.getMultistageReverseProxyIp(ip);
      }
    }

    return Optional.ofNullable(request.getRemoteAddress())
            .map(address -> address.getAddress().getHostAddress())
            .orElse(UNKNOWN);
  }

}
