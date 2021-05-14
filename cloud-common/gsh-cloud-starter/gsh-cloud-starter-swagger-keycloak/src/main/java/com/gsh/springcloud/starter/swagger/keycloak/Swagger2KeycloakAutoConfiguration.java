//package com.gsh.springcloud.starter.swagger.keycloak;
//
//import com.google.common.base.Predicate;
//import com.gsh.springcloud.starter.swagger.keycloak.properties.SwaggerProperties;
//import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.SecurityConfiguration;
//import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static com.google.common.collect.Lists.newArrayList;
//
///**
// * @author gsh
// */
//@Configuration
//@EnableOpenApi
//@EnableOAuth2Client
//@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
//@EnableConfigurationProperties({SwaggerProperties.class, OAuth2ClientProperties.class, KeycloakSpringBootProperties.class})
//public class SwaggerKeycloakAutoConfigurationBak {
//
//  /**
//   * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
//   */
//  private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");
//  private static final String BASE_PATH = "/**";
//
//  @Value("${security.oauth2.client.access-token-uri}")
//  private String tokenUrl;
//
//  @Value("${security.oauth2.client.user-authorization-uri}")
//  private String authorizationUrl;
//
//  private OAuth securitySchema() {
//    List<AuthorizationScope> authorizationScopeList = newArrayList(
//            new AuthorizationScope(HttpMethod.GET.name(), "query resources"),
//            new AuthorizationScope(HttpMethod.POST.name(), "create resources"),
//            new AuthorizationScope(HttpMethod.PUT.name(), "update resources"),
//            new AuthorizationScope(HttpMethod.DELETE.name(), "delete resources"));
//    List<GrantType> grantTypes = newArrayList(
//            new ImplicitGrant(new LoginEndpoint(authorizationUrl), "access_token"),
//            new ResourceOwnerPasswordCredentialsGrant(tokenUrl)
//    );
//    return new OAuth("oauth2_token", authorizationScopeList, grantTypes);
//  }
//
//  private List<SecurityReference> defaultAuth() {
//    List<AuthorizationScope> authorizationScopeList = newArrayList(
//            new AuthorizationScope(HttpMethod.GET.name(), "query resources"));
//    return Collections.singletonList(
//            new SecurityReference("oauth2_token",
//                    authorizationScopeList.toArray(new AuthorizationScope[0])));
//  }
//
//  private SecurityContext securityContext() {
//    return SecurityContext.builder().securityReferences(defaultAuth())
//            .build();
//  }
//
//  @Bean
//  @ConditionalOnMissingBean
//  public SwaggerProperties swaggerProperties() {
//    return new SwaggerProperties();
//  }
//
//  @Bean
//  SecurityConfiguration security(OAuth2ClientProperties oAuth2ClientProperties, KeycloakSpringBootProperties keycloakSpringBootProperties) {
//    return SecurityConfigurationBuilder.builder()
//            .clientId(oAuth2ClientProperties.getClientId())
//            .clientSecret(oAuth2ClientProperties.getClientSecret())
//            .realm(keycloakSpringBootProperties.getRealm())
//            .appName(oAuth2ClientProperties.getClientId())
//            .scopeSeparator(",")
//            .additionalQueryStringParams(null)
//            .useBasicAuthenticationWithAccessCodeGrant(false)
//            .build();
//  }
//
//  @Bean
//  public SecurityScheme apiKey() {
////    return new ApiKey("BearerToken", HttpHeaders.AUTHORIZATION, "header");
//    return new ApiKey("Authorization", HttpHeaders.AUTHORIZATION, "header");
//  }
//
//  @Bean
//  @Primary
//  public Docket docketApi(SwaggerProperties swaggerProperties) {
//    // base-path处理
//    if (swaggerProperties.getBasePath().isEmpty()) {
//      swaggerProperties.getBasePath().add(BASE_PATH);
//    }
//    //noinspection unchecked
//    List<Predicate<String>> basePath = new ArrayList();
////    swaggerProperties.getBasePath().forEach(path -> basePath.add(PathSelectors.ant(path)));
//
//    // exclude-path处理
//    if (swaggerProperties.getExcludePath().isEmpty()) {
//      swaggerProperties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);
//    }
//
//    List<Predicate<String>> excludePath = new ArrayList<>();
////    swaggerProperties.getExcludePath().forEach(path -> excludePath.add(PathSelectors.ant(path)));
//
//    //noinspection Guava
////    return new Docket(DocumentationType.SWAGGER_2)
//    return new Docket(DocumentationType.OAS_30)
//            .host(swaggerProperties.getHost())
//            .apiInfo(apiInfo(swaggerProperties)).select()
//            .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
////            .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
//            .paths(PathSelectors.any())
//            .build()
//            .securityContexts(Collections.singletonList(securityContext()))
//            .securitySchemes(Arrays.asList(securitySchema(), apiKey()))
//            .pathMapping("/");
//  }
//
//
//  private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
//    return new ApiInfoBuilder()
//            .title(swaggerProperties.getTitle())
//            .description(swaggerProperties.getDescription())
//            .license(swaggerProperties.getLicense())
//            .licenseUrl(swaggerProperties.getLicenseUrl())
//            .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
//            .contact(new Contact(swaggerProperties.getContact().getName(),
//                    swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
//            .version(swaggerProperties.getVersion())
//            .build();
//  }
//
//
////  @Bean
////  public KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver(KeycloakSpringBootProperties keycloakSpringBootProperties) {
////    return new CustomKeycloakSpringBootConfigResolver(keycloakSpringBootProperties);
////  }
//
//}
