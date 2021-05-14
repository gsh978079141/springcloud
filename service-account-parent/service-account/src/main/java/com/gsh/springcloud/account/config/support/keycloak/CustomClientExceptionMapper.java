package com.gsh.springcloud.account.config.support.keycloak;

import com.gsh.springcloud.common.exception.InvalidOauthTokenException;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.exception.mapper.ClientExceptionMapper;
import org.keycloak.admin.client.Keycloak;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ext.Provider;

@Provider
@Slf4j
public class CustomClientExceptionMapper implements ClientExceptionMapper<NotAuthorizedException> {

  Keycloak keycloakInstance;

  @Override
  public RuntimeException toException(NotAuthorizedException ex) {
    // TODO Auto-generated method stub
    ex.getResponse().getHeaders().forEach((k, v) -> {
      log.info("admin request response header----->{}:{}", k, v);
    });
    String responseBody = ex.getResponse().readEntity(String.class);
    log.info("response body from admin request is ----->{}", responseBody);
    keycloakInstance.tokenManager().refreshToken();
    return new InvalidOauthTokenException();
  }

  public void setKeycloakInstance(Keycloak keycloakInstance) {
    this.keycloakInstance = keycloakInstance;
  }
}
