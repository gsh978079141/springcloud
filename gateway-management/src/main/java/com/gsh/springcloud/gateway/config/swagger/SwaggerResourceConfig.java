package com.gsh.springcloud.gateway.config.swagger;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取SwaggerResources列表信息，即业务模块列表
 * 列表数据填充在swagger ui左上角的下拉框里
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {
  private static final String SWAGGER_URI = "/v3/api-docs";

  private final RouteLocator routeLocator;
  private final GatewayProperties gatewayProperties;


  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();
    List<String> routes = new ArrayList<>();
    // 只抽取后缀为Swagger的路由信息
    routeLocator.getRoutes().filter(r -> r.getId().endsWith("Swagger")).subscribe(route -> routes.add(route.getId()));

    gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId())).forEach(route -> {
      route.getPredicates().stream()
              .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
              .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
                      predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                              .replace("/**", SWAGGER_URI))));
    });

    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location) {
    log.info("name:{},location:{}", name, location);
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion("2.0");
    return swaggerResource;
  }

}
