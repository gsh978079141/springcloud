package com.gsh.springcloud.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.net.URI;
import java.util.Optional;

@RequestMapping("")
@RestController
public class SwaggerHandler {

  @Autowired(required = false)
  private SecurityConfiguration securityConfiguration;

  @Autowired(required = false)
  private UiConfiguration uiConfiguration;

  private final SwaggerResourcesProvider swaggerResources;

  @Autowired
  public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
    this.swaggerResources = swaggerResources;
  }


  @RequestMapping("/")
  public Mono<Void> hello2(ServerHttpResponse response) {
    return Mono.fromRunnable(() -> {
      response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
      response.getHeaders().setLocation(URI.create("doc.html"));
    });
  }

  @GetMapping("/swagger-resources")
  public Mono<ResponseEntity> swaggerResources() {
    return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
  }

  @GetMapping("/swagger-resources/configuration/security")
  public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
    return Mono.just(new ResponseEntity<>(
            Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
  }

  @GetMapping("/swagger-resources/configuration/ui")
  public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
    return Mono.just(new ResponseEntity<>(
            Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
  }


}
