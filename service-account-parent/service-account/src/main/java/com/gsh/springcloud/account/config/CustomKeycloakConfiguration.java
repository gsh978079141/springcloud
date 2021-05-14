package com.gsh.springcloud.account.config;

import com.alibaba.fastjson.JSON;
import com.gsh.springcloud.account.config.support.keycloak.CustomClientExceptionMapper;
import com.gsh.springcloud.account.config.support.keycloak.KeycloakAdminProperties;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({CustomClientExceptionMapper.class})
@Slf4j
@EnableConfigurationProperties(KeycloakAdminProperties.class)
public class CustomKeycloakConfiguration {

  @Bean
  public Keycloak keycloakInstance(
          KeycloakAdminProperties keycloakAdminProperties,
          CustomClientExceptionMapper customClientExceptionMapper) {
    log.info("keycloakAdminProperties: {}", JSON.toJSONString(keycloakAdminProperties));
    Keycloak keycloakInstance = Keycloak.getInstance(
            keycloakAdminProperties.getServerUrl(),
            "master",
            keycloakAdminProperties.getLoginUsername(),
            keycloakAdminProperties.getLoginPassword(),
            keycloakAdminProperties.getLoginClientId());
    keycloakInstance.tokenManager().setMinTokenValidity(keycloakAdminProperties.getMinTokenValidity());
    customClientExceptionMapper.setKeycloakInstance(keycloakInstance);
    log.info("fetch token from sso server:::::{}", keycloakInstance.tokenManager().getAccessTokenString());
    return keycloakInstance;
  }

  /*@Bean
  public KeycloakCache keycloakCache() {
    return KeycloakMemoryCacheImpl.getInstance();
  }

  @Bean
  public KeycloakCacheManager keycloakCacheManager() {
    return KeycloakMemoryCacheImpl.getInstance();
  }
*/

}
