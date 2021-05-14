//package com.gsh.springcloud.starter.swagger.keycloak.config;
//
//import org.keycloak.adapters.KeycloakDeployment;
//import org.keycloak.adapters.KeycloakDeploymentBuilder;
//import org.keycloak.adapters.spi.HttpFacade;
//import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
//import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
///**
// * @program: springcloud
// * @description:
// * @author: Gsh
// * @create: 2021-05-11 09:32
// **/
//@Configuration
//@Import({KeycloakSpringBootProperties.class})
//public class CustomKeycloakSpringBootConfigResolver extends KeycloakSpringBootConfigResolver {
//
//  private final KeycloakDeployment keycloakDeployment;
//
//  public CustomKeycloakSpringBootConfigResolver(KeycloakSpringBootProperties keycloakSpringBootProperties) {
//    keycloakDeployment = KeycloakDeploymentBuilder.build(keycloakSpringBootProperties);
//  }
//
//  @Override
//  public KeycloakDeployment resolve(HttpFacade.Request facade) {
//    return keycloakDeployment;
//
//  }
//
//}