package com.gsh.springcloud.account.service;

import com.google.common.collect.Maps;
import com.gsh.springcloud.account.constant.AuthorizationConstants;
import com.gsh.springcloud.account.converter.PermissionConverter;
import com.gsh.springcloud.account.converter.ResourceConverter;
import com.gsh.springcloud.account.converter.RoleConverter;
import com.gsh.springcloud.account.exception.ClientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.ScopePermissionResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class KeycloakService {

  private final String DEFAULT_RESOURCE = "Default Resource";

  private final String ROLE_POLICY_PREFIX = "pol.role.";

  private static RealmResource rr = null;
  private static Map<String, ClientResource> cachedClientResourceMap = Maps.newHashMap();
  private static Map<String, Map<String, ScopePermissionResource>> cachedPermissionResourceMap = Maps.newHashMap();
  private static Map<String, Map<String, ResourceRepresentation>> cachedResourceMap = Maps.newHashMap();
  private static Map<String, List<ScopePermissionRepresentation>> cachedPermissionRepresentationMap = Maps.newHashMap();

  @Resource
  Keycloak keycloakInstance;

  @Resource
  private ResourceConverter resourceConverter;

  @Resource
  private PermissionConverter permissionConverter;

  @Resource
  private RoleConverter roleConverter;


  public static void initCache(RealmResource rr,
                               Map<String, ClientResource> cachedClientResourceMap,
                               Map<String, Map<String, ScopePermissionResource>> cachedPermissionResourceMap,
                               Map<String, Map<String, ResourceRepresentation>> cachedResourceMap,
                               Map<String, List<ScopePermissionRepresentation>> cachedPermissionRepresentationMap) {
    KeycloakService.rr = rr;
    KeycloakService.cachedClientResourceMap = cachedClientResourceMap;
    KeycloakService.cachedPermissionResourceMap = cachedPermissionResourceMap;
    KeycloakService.cachedResourceMap = cachedResourceMap;
    KeycloakService.cachedPermissionRepresentationMap = cachedPermissionRepresentationMap;
  }

  protected List<ScopePermissionRepresentation> loadPermReps(String clientId) {
    return cachedPermissionRepresentationMap.get(clientId);
  }

  /***
   *
   * @param clientId
   * @param permissionIds
   * @return key as permission id
   */
  protected Map<String, ScopePermissionRepresentation> loadPermRepMapWithIds(String clientId, List<String> permissionIds) {
    fetchNewestValidToken();
    return cachedPermissionRepresentationMap.get(clientId).stream()
            .filter(item -> permissionIds.contains(item.getId()))
            .collect(Collectors.toMap(ScopePermissionRepresentation::getId, Function.identity()));
  }

  protected ScopePermissionResource loadPermRes(String clientId, String permissionId) {
    fetchNewestValidToken();
    return cachedPermissionResourceMap.get(clientId).get(permissionId);
  }

  /**
   * @param clientId
   * @param permissionIds
   * @return
   */
  protected Map<String, ScopePermissionResource> loadPermResMapWithIds(String clientId, List<String> permissionIds) {
    fetchNewestValidToken();
    Map<String, ScopePermissionResource> resourceMap = cachedPermissionResourceMap.get(clientId);
    return Maps.filterKeys(resourceMap, permissionIds::contains);
  }


  public ResourceRepresentation loadResourceWithId(String clientId, String resourceId, boolean loadNewToken) {
    if (loadNewToken) {
      fetchNewestValidToken();
    }
    return cachedResourceMap.get(clientId).get(resourceId);
  }

  protected Map<String, ResourceRepresentation> loadResourceRepsMap(String clientId) {
    fetchNewestValidToken();
    return cachedResourceMap.get(clientId);
  }

  protected RealmResource realmResource() {
    fetchNewestValidToken();
    return rr;
  }

  protected ClientResource clientResource(String clientId) {
    fetchNewestValidToken();
    ClientResource cr = cachedClientResourceMap.get(clientId);
    if (Objects.isNull(cr)) {
      throw new ClientNotFoundException(clientId);
    }
    return cr;
  }

  protected UserRepresentation loadUserById(String userId) {
    fetchNewestValidToken();
    return realmResource().users().get(userId).toRepresentation();
  }

  protected String buildPolicyPrefix(String clientId) {
    return AuthorizationConstants.POLICY_PREFIX;
  }

  protected String fetchNewestValidToken() {
    String token = keycloakInstance.tokenManager().refreshToken().getToken();
    log.info("the token is::::::{}", token);
    return token;
  }

  protected RealmResource getRealmByName(String realmName) {
    fetchNewestValidToken();
    return keycloakInstance.realm(realmName);
  }


}
