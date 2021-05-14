package com.gsh.springcloud.account.config.support.keycloak;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

@ConfigurationProperties("keycloak-admin.params")
@Data
@Order(0)
public class KeycloakAdminProperties {

  private String serverUrl;

  private String loginUsername;

  private String loginPassword;

  private String loginClientId;

  private String loginClientSecret;

  private String managedRealm;

  private int minTokenValidity;
}
