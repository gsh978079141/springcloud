package com.gsh.springcloud.starter.swagger;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.gsh.springcloud.starter.swagger.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gsh
 */
@Configuration
@EnableKnife4j
@EnableOpenApi
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

  /**
   * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
   */
  private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");
  private static final String BASE_PATH = "/**";

  @Bean
  @ConditionalOnMissingBean
  public SwaggerProperties swaggerProperties() {
    return new SwaggerProperties();
  }


  @Bean
  @Primary
  public Docket docketApi(SwaggerProperties swaggerProperties) {
    // base-path处理
    if (swaggerProperties.getBasePath().isEmpty()) {
      swaggerProperties.getBasePath().add(BASE_PATH);
    }


    //noinspection unchecked
    final List<java.util.function.Predicate<String>> basePath = new ArrayList();
    swaggerProperties.getBasePath().forEach(path -> basePath.add(PathSelectors.ant(path)));


    // exclude-path处理
    if (swaggerProperties.getExcludePath().isEmpty()) {
      swaggerProperties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);
    }

    final List<java.util.function.Predicate<String>> excludePath = new ArrayList<>();
    swaggerProperties.getExcludePath().forEach(path -> excludePath.add(PathSelectors.ant(path)));

    //noinspection Guava
    return new Docket(DocumentationType.OAS_30)
            .host(swaggerProperties.getHost())
            .apiInfo(apiInfo(swaggerProperties))
            .select()
//            .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
            //配置扫面的包路径或者其他规则
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
//            .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
            .build()
            .pathMapping("/");
  }

  private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
    return new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .license(swaggerProperties.getLicense())
            .licenseUrl(swaggerProperties.getLicenseUrl())
            .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
            .contact(new Contact(swaggerProperties.getContact().getName(),
                    swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
            .version(swaggerProperties.getVersion())
            .build();
  }

}
