package com.gsh.springcloud.gateway.config.keycloak;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak-admin.params")
@Setter
@Getter
public class KeycloakAdminProperties {

  private String serverUrl;

  private String loginUsername;

  private String loginPassword;

  private String loginClientId;

  private int minTokenValidity;

  /**
   * 需要管理的业务相关realm
   */
  private String managedRealm;

  /**
   * 需要管理的业务clientId
   */
  private String managedClientId;


}
