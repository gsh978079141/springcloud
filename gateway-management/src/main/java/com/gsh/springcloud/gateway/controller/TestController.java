package com.gsh.springcloud.gateway.controller;

import com.gsh.springcloud.common.vo.JsonResult;
import com.gsh.springcloud.gateway.config.gateway.PermissionUriMapProperties;
import com.gsh.springcloud.gateway.domain.TestUrlParseReq;
import com.gsh.springcloud.gateway.domain.UrlAndPermission;
import com.gsh.springcloud.gateway.util.UrlParseUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

  @Resource
  private PermissionUriMapProperties permissionUriMapProperties;


  @ApiOperation(value = "返回url的绑定权限", nickname = "getPermissionFromUrl", notes = "返回url的绑定权限")
  @PostMapping(value = "getPermissionFromUrl")
  public Mono<JsonResult> getPermissionFromUrl(@Valid @RequestBody TestUrlParseReq req) {
    List<UrlAndPermission> urlAndPermissions = permissionUriMapProperties.getUrlPermission();
    Optional<String[]> optionalPermission = UrlParseUtil.getPermissionFromUrl(req.getUrl(), req.getMethod(), urlAndPermissions);
    if (optionalPermission.isPresent()) {
      return Mono.just(JsonResult.success(optionalPermission.get()));
    }
    return Mono.just(JsonResult.success("no permission bound"));
  }


}
