package com.gsh.springcloud.account.config.support.keycloak;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springcloud
 * @description:
 * @author: Gsh
 * @create: 2021-05-11 09:32
 **/
@Configuration
public class CustomKeycloakSpringBootConfigResolver extends KeycloakSpringBootConfigResolver {

  private final KeycloakDeployment keycloakDeployment;

  public CustomKeycloakSpringBootConfigResolver(KeycloakSpringBootProperties keycloakSpringBootProperties) {
    keycloakDeployment = KeycloakDeploymentBuilder.build(keycloakSpringBootProperties);
  }

  @Override
  public KeycloakDeployment resolve(HttpFacade.Request facade) {
    return keycloakDeployment;

  }

}