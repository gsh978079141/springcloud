package com.gsh.springcloud.gateway.service.impl;

import com.gsh.springcloud.gateway.domain.UserDto;
import com.gsh.springcloud.gateway.domain.UserPrincipal;
import com.gsh.springcloud.gateway.keycloak.AuthDataCache;
import com.gsh.springcloud.gateway.keycloak.KeycloakHandlerService;
import com.gsh.springcloud.gateway.keycloak.UserCache;
import com.gsh.springcloud.gateway.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


/**
 * @author gsh
 */
@Service
public class AuthServiceImpl implements AuthService {

  @Resource
  private KeycloakHandlerService keycloakHandlerService;

  @Resource
  private AuthDataCache authDataCache;

  @Resource
  private UserCache userCache;

  @Override
  public Optional<UserPrincipal> verifyToken(String token) {
    return keycloakHandlerService.verifyToken(token);
  }

  @Override
  public Optional<UserDto> findUser(String username) {
    UserDto cachedUser = userCache.get(username);
    if (Objects.nonNull(cachedUser)) {
      return Optional.of(cachedUser);
    }
    Optional<UserDto> optionalUser = keycloakHandlerService.findUser(username);
    optionalUser.ifPresent(userDto -> userCache.put(userDto));
    return optionalUser;
  }

  @Override
  public boolean hasPermissionInRoles(String[] permissions, Set<String> roles) {
    return authDataCache.hasPermissionInRoles(permissions, roles);
  }

}
