package com.gsh.springcloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.gsh.springcloud.common.exception.CommonExceptionEnum;
import com.gsh.springcloud.common.vo.JsonResult;
import com.gsh.springcloud.gateway.config.gateway.CustomGatewayProperties;
import com.gsh.springcloud.gateway.config.gateway.PermissionUriMapProperties;
import com.gsh.springcloud.gateway.domain.UrlAndPermission;
import com.gsh.springcloud.gateway.domain.UserDto;
import com.gsh.springcloud.gateway.domain.UserPrincipal;
import com.gsh.springcloud.gateway.service.AuthService;
import com.gsh.springcloud.gateway.util.UrlParseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 请求url权限校验(旧版本)：与AccessAuthGatewayFilter比较取其中一个
 *
 * @author gsh
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({CustomGatewayProperties.class, PermissionUriMapProperties.class})
public class AccessControlFilter implements GlobalFilter, Ordered {

  @Resource
  private CustomGatewayProperties customGatewayProperties;

  @Resource
  private PermissionUriMapProperties permissionUriMapProperties;

  @Resource
  private AuthService authService;


  private boolean shouldNotFilter(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    String url = request.getPath().value();
    if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".html")) {
      return true;
    }

    //  判断url是否是开放url， 开放url不需要验证
    if (StrUtil.containsAny(url, customGatewayProperties.getIgnoredPath())) {
      log.info("url [{}] is in ignored path list,no need to auth", url);
      return true;
    }
    return false;
  }


  /**
   * 1.首先网关检查token是否有效，无效直接返回401，不调用签权服务 2.调用签权服务器看是否对该请求有权限，有权限进入下一个filter，没有权限返回401
   *
   * @param exchange
   * @param chain
   * @return
   */
  @SneakyThrows
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (shouldNotFilter(exchange)) {
      return returnMono(chain, exchange);
    }
    ServerHttpRequest request = exchange.getRequest();
    String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    String url = request.getPath().value();
    String scope = request.getMethodValue();

    // 检查请求头里认证数据是否存在
    if (StrUtil.isBlank(authentication)) {
      log.info("url [{}]，has no Authorization in headers", url);
      return returnFalse(exchange, String.valueOf(CommonExceptionEnum.INVALID_ACCESS_TOKEN.getStatus()), CommonExceptionEnum.INVALID_ACCESS_TOKEN.getCode(), HttpStatus.UNAUTHORIZED);
    }

    authentication = StrUtil.replaceIgnoreCase(authentication, "Bearer ", "");
    Optional<UserPrincipal> optionalUserPrincipal = authService.verifyToken(authentication);
    if (!optionalUserPrincipal.isPresent()) {
      log.info("url [{}]，token is not valid...", url);
      return returnFalse(exchange, String.valueOf(CommonExceptionEnum.INVALID_ACCESS_TOKEN.getStatus()), CommonExceptionEnum.INVALID_ACCESS_TOKEN.getCode(), HttpStatus.UNAUTHORIZED);
    }
    UserPrincipal userPrincipal = optionalUserPrincipal.get();
    log.info("url [{}]，verify token success, userPrincipal:{}", url, JSON.toJSONString(userPrincipal));
    String userId = userPrincipal.getId();
    String username = userPrincipal.getUsername();
    exchange.getAttributes().put("username", username);

    // 在请求头里添加用户信息
    Map<String, String> userAttributes = Maps.newHashMap();
    userAttributes.put("sso_id", userId);
    userAttributes.put("username", userPrincipal.getUsername());
    userAttributes.put("real_name", userPrincipal.getGivenName());
    Optional<UserDto> optionalUser = authService.findUser(username);
    if (!optionalUser.isPresent()) {
      return returnFalse(exchange, String.valueOf(CommonExceptionEnum.ACCESS_DENIED.getStatus()), CommonExceptionEnum.ACCESS_DENIED.getCode(), HttpStatus.FORBIDDEN);
    }
    UserDto user = optionalUser.get();
    userAttributes.putAll(user.getAttributes());
    userAttributes.forEach((key, value) -> request.mutate().header(key, value));

    // 有些url无需控制权限,登录用户均能访问
    if (StrUtil.containsAny(url, customGatewayProperties.getIgnoredPermissionPath())) {
      return returnMono(chain, exchange);
    }
    // >>>>>>>>>>>用户鉴权开始<<<<<<<<
    // 将当前请求url解析成对应的权限
    List<UrlAndPermission> urlAndPermissions = permissionUriMapProperties.getUrlPermission();
    Optional<String[]> optionalPermission = UrlParseUtil.getPermissionFromUrl(url, scope, urlAndPermissions);
    if (!optionalPermission.isPresent()) {
      log.warn("url[{}], not found bound permission", url);
      return returnFalse(exchange, CommonExceptionEnum.ACCESS_DENIED.getCode(), CommonExceptionEnum.ACCESS_DENIED.getCode(), HttpStatus.FORBIDDEN);
    }
    String[] permissions = optionalPermission.get();
    log.info("url:[{}] -> permission:[{}]", url, permissions);


    if (!authService.hasPermissionInRoles(permissions, userPrincipal.getRoles())) {
      log.info("user:[{}] has no permission[{}]", username, permissions);
      return returnFalse(exchange, CommonExceptionEnum.ACCESS_DENIED.getCode(), CommonExceptionEnum.ACCESS_DENIED.getCode(), HttpStatus.FORBIDDEN);
    }

    return returnMono(chain, exchange.mutate().request(request).build());
  }


  Mono<Void> returnFalse(ServerWebExchange exchange, String code, String message, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    JsonResult jsonResult = new JsonResult();
    jsonResult.setSuccess(false);
    jsonResult.setCode(code);
    jsonResult.setMessage(message);
    byte[] bits = ((JSONObject) JSONObject.toJSON(jsonResult)).toJSONString().getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = response.bufferFactory().wrap(bits);
    response.setStatusCode(httpStatus);
    //指定编码，否则在浏览器中会中文乱码
    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
    return response.writeWith(Mono.just(buffer));
  }


  private Mono<Void> returnMono(GatewayFilterChain chain, ServerWebExchange exchange) {
    return chain.filter(exchange);
  }


  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }


}
