package com.gsh.springcloud.gateway.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 就绪探针
 * 服务启动后需要从keycloak加载完所有权限数据才能就绪，即对外提供服务
 * 供k8s检测服务是否就绪
 *
 * @author jun
 */
@RequestMapping("")
@RestController
public class ProbeHandler {


  @GetMapping("is-ready")
  public Mono<ResponseEntity<String>> isReady() {
    return Mono.just((new ResponseEntity<>("ok", HttpStatus.OK)));
  }


}
