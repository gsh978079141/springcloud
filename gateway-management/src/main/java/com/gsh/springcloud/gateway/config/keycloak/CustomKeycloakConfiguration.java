package com.gsh.springcloud.gateway.config.keycloak;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


/**
 * @author maj
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(KeycloakAdminProperties.class)
public class CustomKeycloakConfiguration {

  @Bean
  public Keycloak keycloakInstance(
          KeycloakAdminProperties keycloakAdminProperties) {
    log.info("keycloakAdminProperties: {}", JSON.toJSONString(keycloakAdminProperties));

    ResteasyClient resteasyClient = new ResteasyClientBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectionPoolSize(20)
            .disableTrustManager()
            .build();

    Keycloak keycloakInstance = KeycloakBuilder.builder()
            .serverUrl(keycloakAdminProperties.getServerUrl())
            .realm("master")
            .username(keycloakAdminProperties.getLoginUsername())
            .password(keycloakAdminProperties.getLoginPassword())
            .clientId(keycloakAdminProperties.getLoginClientId())
            .resteasyClient(resteasyClient)
            .build();
    keycloakInstance.tokenManager().setMinTokenValidity(keycloakAdminProperties.getMinTokenValidity());
    keycloakInstance.tokenManager().getAccessToken();
    return keycloakInstance;
  }

}
