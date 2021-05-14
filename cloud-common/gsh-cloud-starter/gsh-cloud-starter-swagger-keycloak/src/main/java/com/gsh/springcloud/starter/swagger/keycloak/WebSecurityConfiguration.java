package com.gsh.springcloud.starter.swagger.keycloak;

import com.google.common.collect.Lists;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springboot.KeycloakBaseSpringBootConfiguration;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.security.Principal;


/**
 * @author gsh
 */
@KeycloakConfiguration
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
@Order(99)
public class WebSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

  @Resource
  ClientRegistrationRepository clientRegistrationRepository;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {

    KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Primary
  protected OAuth2ProtectedResourceDetails clientCredentialsResourceDetails() {
    ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
    ClientRegistration clientRegistration = clientRegistrationRepository
            .findByRegistrationId("keycloak");
    resource.setAccessTokenUri(clientRegistration.getProviderDetails().getTokenUri());
    resource.setClientId(clientRegistration.getClientId());
    resource.setClientSecret(clientRegistration.getClientSecret());
    resource.setClientAuthenticationScheme(AuthenticationScheme.header);
    resource.setAuthenticationScheme(AuthenticationScheme.header);
    resource.setScope(Lists.newArrayList(clientRegistration.getScopes()));
    return resource;
  }

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  /* this defines that we want to use the Spring Boot properties file support instead of the default keycloak.json */
  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(
                    "/experiment-rooms/tables/detail/**",
                    "/pub/**",
                    "/**/name/**",
                    "/**/client/**",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/doc.html",
                    "/swagger-ui.html",
                    "/swagger-ui/index.html",
                    "/swagger-ui/**",
                    "/static/**",
                    "/configuration/ui",
                    "/v2/**",
                    "/v3/**",
                    "/actuator/**",
                    "/error",
                    "/")
            .permitAll()
            .anyRequest()
            .authenticated();
  }


  /**
   * Allows to inject requests scoped wrapper for {@link KeycloakSecurityContext}.
   * <p>
   * Returns the {@link KeycloakSecurityContext} from the Spring {@link ServletRequestAttributes}'s
   * {@link Principal}.
   * <p>
   * The principal must support retrieval of the KeycloakSecurityContext, so at this point, only
   * {@link KeycloakPrincipal} values and {@link KeycloakAuthenticationToken} are supported.
   *
   * @return the current <code>KeycloakSecurityContext</code>
   */
  @Bean
  @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public KeycloakSecurityContext provideKeycloakSecurityContext() {

    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
    Principal principal = attributes.getRequest().getUserPrincipal();
    if (principal == null) {
      return null;
    }

    if (principal instanceof KeycloakAuthenticationToken) {
      principal = Principal.class
              .cast(KeycloakAuthenticationToken.class.cast(principal).getPrincipal());
    }

    if (principal instanceof KeycloakPrincipal) {
      return KeycloakPrincipal.class.cast(principal).getKeycloakSecurityContext();
    }

    return null;
  }

  /**
   * Ensures the correct registration of KeycloakSpringBootConfigResolver when Keycloaks
   * AutoConfiguration is explicitly turned off in application.yml {@code keycloak.enabled: false}.
   */
  @Configuration
  static class CustomKeycloakBaseSpringBootConfiguration extends
          KeycloakBaseSpringBootConfiguration {

  }
}

