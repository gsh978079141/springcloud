//package com.gsh.springcloud.order.config.swagger;
//
//import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.SecurityConfiguration;
//import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import javax.annotation.Resource;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import static com.google.common.collect.Lists.newArrayList;
//
///**
// * @author gsh
// */
//@EnableSwagger2
//@Configuration
//@EnableConfigurationProperties(SwaggerProperties.class)
//public class SwaggerConfiguration {
//
//  @Resource
//  SwaggerProperties swaggerProperties;
//
//  @Resource
//  KeycloakSpringBootProperties keycloakSpringBootProperties;
//
//  @Resource
//  OAuth2ClientProperties oAuth2ClientProperties;
//
//  @Value("${security.oauth2.client.access-token-uri}")
//  private String tokenUrl;
//
//  @Value("${security.oauth2.client.user-authorization-uri}")
//  private String authorizationUrl;
//
//  private Parameter headerParam(SwaggerProperties.GlobalParameter globalParameter) {
//    return new ParameterBuilder()
//        .name(globalParameter.getName())
//        .description(globalParameter.getDescription())
//        .modelRef(new ModelRef(globalParameter.getDataType()))
//        .defaultValue(globalParameter.getDefaultValue())
//        .parameterType(globalParameter.getParamType())
//        .required(globalParameter.isRequired())
//        .build();
//  }
//
//  @Bean
//  public Docket createRestApi() {
//    List<Parameter> pars = swaggerProperties.getGlobalParameters().stream()
//        .map(this::headerParam)
//        .filter(item -> {
//          List<String> names = swaggerProperties.getIgnoreGlobalParameters();
//          return names.stream().noneMatch(i -> i.trim().equals(item.getName()));
//        })
//        .collect(Collectors.toList());
//    return new Docket(DocumentationType.SWAGGER_2)
////        .host(swaggerProperties.getHost())
//        .apiInfo(apiInfo())
//        .globalOperationParameters(pars)
//        .directModelSubstitute(LocalDate.class, String.class)
//        .genericModelSubstitutes(ResponseEntity.class)
//        .useDefaultResponseMessages(false)
//        .select()
//        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//        .paths(PathSelectors.any())
//        .build()
//        .securityContexts(Collections.singletonList(securityContext()))
//        .securitySchemes(Arrays.asList(securitySchema(), apiKey()));
//  }
//
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//        .title(swaggerProperties.getApiInfo().getTitle())
//        .contact(swaggerProperties.getApiInfo().getContact())
//        .version(swaggerProperties.getApiInfo().getVersion())
//        .description(swaggerProperties.getApiInfo().getDescription())
//        .build();
//  }
//
//  @Bean
//  public SecurityScheme apiKey() {
//    return new ApiKey("BearerToken", HttpHeaders.AUTHORIZATION, "header");
//  }
//
//  private OAuth securitySchema() {
//    List<AuthorizationScope> authorizationScopeList = newArrayList(
//        new AuthorizationScope(HttpMethod.GET.name(), "query resources"),
//        new AuthorizationScope(HttpMethod.POST.name(), "create resources"),
//        new AuthorizationScope(HttpMethod.PUT.name(), "update resources"),
//        new AuthorizationScope(HttpMethod.DELETE.name(), "delete resources"));
//    List<GrantType> grantTypes = newArrayList(
//        new ImplicitGrant(new LoginEndpoint(authorizationUrl), "access_token"),
//        new ResourceOwnerPasswordCredentialsGrant(tokenUrl)
//    );
//    return new OAuth("oauth2_token", authorizationScopeList, grantTypes);
//  }
//
//  private SecurityContext securityContext() {
//    return SecurityContext.builder().securityReferences(defaultAuth())
//        .build();
//  }
//
//  private List<SecurityReference> defaultAuth() {
//    List<AuthorizationScope> authorizationScopeList = newArrayList(
//        new AuthorizationScope(HttpMethod.GET.name(), "query resources"));
//    return Collections.singletonList(
//        new SecurityReference("oauth2_token",
//            authorizationScopeList.toArray(new AuthorizationScope[0])));
//  }
//
//
//  @Bean
//  SecurityConfiguration security() {
//    return SecurityConfigurationBuilder.builder()
//        .clientId(oAuth2ClientProperties.getClientId())
//        .clientSecret(oAuth2ClientProperties.getClientSecret())
//        .realm(keycloakSpringBootProperties.getRealm())
//        .appName(oAuth2ClientProperties.getClientId())
//        .scopeSeparator(",")
//        .additionalQueryStringParams(null)
//        .useBasicAuthenticationWithAccessCodeGrant(false)
//        .build();
//  }
//
//}
