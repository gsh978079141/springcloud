package com.gsh.springcloud.account.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gsh.springcloud.account.constant.AuthorizationConstants;
import com.gsh.springcloud.account.constant.RoleConstants;
import com.gsh.springcloud.account.exception.RoleNamesInvalidException;
import com.gsh.springcloud.account.exception.RoleNotFoundException;
import com.gsh.springcloud.account.request.PermissionRoleBindReq;
import com.gsh.springcloud.account.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;
import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@Slf4j
public class KeycloakHelper extends KeycloakService {


  private static final String ROLE_NAME_PREFIX = "pol.role.";

  /**
   * convert from request object to keycloak role collection
   *
   * @param clientId if null or empty, we will fetch realm roles only
   * @param req      request
   * @return
   */
  public List<RoleRepresentation> listAllRolesFromRequest(String clientId, PermissionRoleBindReq req) {
    RealmResource rr = realmResource();
    List<RoleRepresentation> roles = Lists.newArrayList();
    // load realm roles
    if (CollectionUtils.isNotEmpty(req.getRoleNames())) {
      roles.addAll(rr.roles().list().stream()
              .filter(item -> req.getRoleNames().contains(item.getName()))
              .collect(Collectors.toList()));
    }
    // load client roles
    if (StringUtils.isNotBlank(clientId) && CollectionUtils.isNotEmpty(req.getClientRoleNames())) {
      ClientResource cr = clientResource(clientId);
      roles.addAll(cr.roles().list().stream()
              .filter(item -> req.getClientRoleNames().contains(item.getName()))
              .collect(Collectors.toList()));
    }
    if (CollectionUtils.isEmpty(roles)) {
      throw new RoleNamesInvalidException();
    }
    return roles;
  }

  /**
   * @param clientId if null or empty the method will think it's realm role then create role policy for each client
   * @param roleName
   */
  public void createRolePolicy(String clientId, String roleName) {
    if (StringUtils.isAnyBlank(roleName, clientId)) {
      return;
    }
    RolePolicyRepresentation rpr = new RolePolicyRepresentation();
    rpr.setName(super.buildPolicyPrefix(clientId).concat(roleName));
    rpr.addClientRole(clientId, roleName);
    clientResource(clientId).authorization().policies().role().create(rpr);
  }

  /**
   * @param clientId
   * @param permissionId
   * @return
   */
  public List<RoleRepresentation> listRolesOfPermission(String clientId, String permissionId) {
    ClientResource cr = super.clientResource(clientId);
    return super.loadPermRes(clientId, permissionId).associatedPolicies().stream()
            .filter(i -> AuthorizationConstants.ROLE.equals(i.getType()))
            .map(PolicyRepresentation::getId)
            .map(i -> {
              RolePolicyRepresentation representation = cr.authorization().policies().role().findById(i).toRepresentation();
              return representation.getRoles();
            })
            .map(Lists::newArrayList)
            .flatMap(List::stream)
            .filter(Objects::nonNull)
            .map(i -> {
              List<RoleRepresentation> rs = realmResource().roles().list().stream()
                      .filter(it -> it.getId().equals(i.getId()))
                      .collect(Collectors.toList());
              if (CollectionUtils.isEmpty(rs)) {
                rs.addAll(cr.roles().list().stream()
                        .filter(it -> it.getId().equals(i.getId()))
                        .collect(Collectors.toList()));
              }
              return rs;
            })
            .flatMap(List::stream)
            .collect(Collectors.toList());
  }


  /**
   * @param userId
   * @param clientId
   * @return
   */
  public Set<String> listClientRolesOfUser(String userId, String clientId) {
    return realmResource().users().get(userId).roles()
            .clientLevel(clientResource(clientId).toRepresentation().getId()).listAll().stream()
            .map(RoleRepresentation::getName)
            .filter(item -> !RoleConstants.IGNORE_ROLE_NAMES.contains(item))
            .collect(Collectors.toSet());
  }

  /**
   * @param clientId
   * @param roleNames
   * @return
   */
  public List<ScopePermissionRepresentation> listPermissionsOfRoleNames(String clientId, String... roleNames) {
    List<ScopePermissionRepresentation> permissionList = Lists.newArrayList();

    //get policy by role name
    for (String roleN : roleNames) {
      PolicyRepresentation policyRepresentation = loadRolePolicyMapByClientIdAndRoleName(clientId, roleN);
      if (policyRepresentation == null) {
        log.info("policyRepresentation is null ");
        continue;
      }

      log.info("policyRepresentation name {}", policyRepresentation.getName());

      List<ScopePermissionRepresentation> clientScopePermission = super.loadPermReps(clientId);
      for (ScopePermissionRepresentation scopePermissionRepresentation : clientScopePermission) {
        if (scopePermissionRepresentation == null || scopePermissionRepresentation.getPolicies() == null) {
          continue;
        }

        if (scopePermissionRepresentation.getPolicies().stream().anyMatch(item -> item.equals(policyRepresentation.getId()))) {
          permissionList.add(scopePermissionRepresentation);
        }
      }
    }
    return permissionList;
  }

  public PolicyRepresentation loadRolePolicyMapByClientIdAndRoleName(String clientId, String roleName) {

    PolicyRepresentation result = clientResource(clientId).authorization().policies().findByName(AuthorizationConstants.POLICY_PREFIX + roleName);

    log.info(" loadRolePolicyMapByClientIdAndRoleName {} ", result);
    return result;
  }


  /**
   * @param clientId
   * @param permissionId
   * @return
   */
  public List<ResourceRepresentation> listResourcesOfPermission(String clientId, String permissionId) {
    return super.loadPermRes(clientId, permissionId).resources().stream()
            .map(item -> super.loadResourceWithId(clientId, item.getId(), true))
            .collect(Collectors.toList());
  }

  /**
   * load all role policies in Map on client
   *
   * @param clientId
   * @return key is roleId, value is PolicyRepresentation
   */
  public Map<String, PolicyRepresentation> loadRolePolicyMap(String clientId) {
    log.info("load all role policies in Map on client:{}", clientId);

//    Map<String, PolicyRepresentation> map = new HashMap<>();


    List<PolicyRepresentation> policies = clientResource(clientId).authorization().policies().policies();
    policies.forEach(policyRepresentation -> {
      log.info("policyRepresentation: {}", JSON.toJSONString(policyRepresentation));
      policyRepresentation.getConfig().entrySet().forEach(stringStringEntry -> {
        log.info("config: [{}]->[{}]", stringStringEntry.getKey(), stringStringEntry.getValue());
      });
    });

    return policies.stream()
            .filter(item -> AuthorizationConstants.ROLE.equals(item.getType()))
            .collect(Collectors.toMap(item -> {
              String rolesString = item.getConfig().get(AuthorizationConstants.ROLE_S);
              JSONArray array = JSON.parseArray(rolesString);
              log.debug("roles array of policy named ::::::{} is :::::::{}", item.getName(), array.toString());
              return array.getJSONObject(0).getString(AuthorizationConstants.ID);
            }, Function.identity()));


  }

  /**
   * load all role policies in Map on client
   *
   * @param clientId
   * @return key is roleId, value is PolicyRepresentation
   */
  public PolicyRepresentation loadRolePolicyMapByInit(String clientId, String roleName) {

    PolicyRepresentation result = clientResource(clientId).authorization().policies().findByName(ROLE_NAME_PREFIX + roleName);

    return result;
  }

  public PolicyRepresentation getPolicyWithRoleName(String clientId, String roleName) {
    return clientResource(clientId).authorization().policies().policies().stream()
            .filter(item -> AuthorizationConstants.ROLE.equals(item.getType()))
            .filter(item -> item.getName().equals(super.buildPolicyPrefix(clientId).concat(roleName)))
            .findFirst().orElse(null);
  }

  /**
   * @param clientId
   * @param roleNames
   * @return
   */
  public List<ScopePermissionRepresentation> listPermissionsOfRoleByRoleNames(String clientId, String... roleNames) {
    ClientResource cr = clientResource(clientId);
    List<ScopePermissionRepresentation> ps = Lists.newArrayList();
    List<RoleRepresentation> roles = cr.roles().list().stream()
            .filter(item -> Lists.newArrayList(roleNames).contains(item.getName()))
            .collect(Collectors.toList());
    Map<String, PolicyRepresentation> roleMap = loadRolePolicyMap(clientId);

    roleMap = Maps.filterKeys(roleMap, key -> roles.stream().map(RoleRepresentation::getId).collect(Collectors.toList()).contains(key));
    if (MapUtils.isNotEmpty(roleMap)) {
      List<String> pIds = roleMap.values().stream().map(PolicyRepresentation::getId).collect(Collectors.toList());
      ps = super.loadPermReps(clientId).stream()
              .filter(item -> {
                ScopePermissionResource res = super.loadPermRes(clientId, item.getId());
                List<String> policyIds = res.associatedPolicies().stream()
                        .map(PolicyRepresentation::getId)
                        .collect(Collectors.toList());
                for (String id : policyIds) {
                  if (pIds.contains(id)) {
                    return true;
                  }
                }
                return false;
              })
              .collect(Collectors.toList());
    }
    return ps;
  }

  public List<ScopePermissionRepresentation> listPermissionsOfRoleByRoleNames(String clientId, ClientResource clientResource, String... roleNames) {
    List<ScopePermissionRepresentation> ps = Lists.newArrayList();
    List<RoleRepresentation> roles = clientResource.roles().list().stream()
            .filter(item -> Lists.newArrayList(roleNames).contains(item.getName()))
            .collect(Collectors.toList());
    Map<String, PolicyRepresentation> roleMap = loadRolePolicyMap(clientId);

    roleMap = Maps.filterKeys(roleMap, key -> roles.stream().map(RoleRepresentation::getId).collect(Collectors.toList()).contains(key));
    if (MapUtils.isNotEmpty(roleMap)) {
      List<String> pIds = roleMap.values().stream().map(PolicyRepresentation::getId).collect(Collectors.toList());
      ps = super.loadPermReps(clientId).stream()
              .filter(item -> {
                ScopePermissionResource res = super.loadPermRes(clientId, item.getId());
                List<String> policyIds = res.associatedPolicies().stream()
                        .map(PolicyRepresentation::getId)
                        .collect(Collectors.toList());
                for (String id : policyIds) {
                  if (pIds.contains(id)) {
                    return true;
                  }
                }
                return false;
              })
              .collect(Collectors.toList());
    }
    return ps;
  }

  public Map<String, List<ScopePermissionRepresentation>> getRolesPermissionMap(String clientId, String... roleNames) {
    Map<String, List<ScopePermissionRepresentation>> rolePermissionMap = Maps.newHashMap();
    ClientResource cr = clientResource(clientId);
    List<RoleRepresentation> roles = cr.roles().list().stream()
            .filter(item -> Lists.newArrayList(roleNames).contains(item.getName()))
            .collect(Collectors.toList());
    Map<String, PolicyRepresentation> roleMap = loadRolePolicyMap(clientId);
    roleMap = Maps.filterKeys(roleMap, key -> roles.stream().map(RoleRepresentation::getId).collect(Collectors.toList()).contains(key));
    if (MapUtils.isNotEmpty(roleMap)) {
      List<String> pIds = roleMap.values().stream().map(PolicyRepresentation::getId).collect(Collectors.toList());
      Map<String, PolicyRepresentation> finalRoleMap = roleMap;
      super.loadPermReps(clientId)
              .forEach(item -> {
                ScopePermissionResource res = super.loadPermRes(clientId, item.getId());
                List<String> policyIds = res.associatedPolicies().stream()
                        .map(PolicyRepresentation::getId)
                        .collect(Collectors.toList());
                for (String id : policyIds) {
                  if (pIds.contains(id)) {
                    String roleName = finalRoleMap.entrySet().stream()
                            .filter(value -> Objects.equals(value.getValue().getId(), id))
                            .findFirst().orElse(null).getKey();
                    List<ScopePermissionRepresentation> rolePermissionMapOrDefault = rolePermissionMap.getOrDefault(roleName, Lists.newArrayList());
                    rolePermissionMapOrDefault.add(item);
                    rolePermissionMap.put(roleName, rolePermissionMapOrDefault);
                  }
                }
              });
    }
    return rolePermissionMap;
  }

  /**
   * @param roleId
   * @return
   */
  public RoleRepresentation loadRealmRole(String roleId) {
    RoleRepresentation rr = realmResource().roles().list().stream()
            .filter(item -> item.getId().equals(roleId))
            .findFirst()
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    return rr;
  }

  /**
   * @param clientId
   * @param roleId
   * @return
   */
  public RoleRepresentation loadClientRole(String clientId, String roleId) {
    RoleRepresentation rr = clientResource(clientId).roles().list().stream()
            .filter(item -> item.getId().equals(roleId))
            .findFirst()
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    return rr;
  }

  public RoleRepresentation loadClientRoles(String clientId, List<String> roleIds) {
    RoleRepresentation rr = clientResource(clientId).roles().list().stream()
            .filter(item -> roleIds.contains(item.getId()))
            .findAny()
            .orElseThrow(() -> new RoleNotFoundException(roleIds));
    return rr;
  }


  /**
   * @param userId
   * @param clientRolesMap
   */
  public void bindClientRoles4user(String userId, Map<String, List<String>> clientRolesMap) {
    log.info("binding client roles are::::{}", JSON.toJSONString(clientRolesMap));
    Map<String, String> clientUuidMappings = realmResource().clients().findAll().stream()
            .filter(item -> clientRolesMap.keySet().contains(item.getClientId()))
            .collect(Collectors.toMap(ClientRepresentation::getClientId, ClientRepresentation::getId));

    RoleMappingResource rmr = realmResource().users().get(userId).roles();
    Map<String, RoleScopeResource> rsrMappings = clientUuidMappings.keySet().stream()
            .collect(
                    Collectors.toMap(
                            item -> clientUuidMappings.get(item),
                            item -> rmr.clientLevel(clientUuidMappings.get(item)))
            );
    // clear old
    rsrMappings.forEach((k, v) -> {
      v.remove(v.listAll());
    });
    // add new
    clientRolesMap.forEach((k, v) -> {
      List<RoleRepresentation> roles = clientResource(k).roles().list().stream()
              .filter(item -> v.contains(item.getName()))
              .collect(Collectors.toList());
      log.info("roles are::::{} of client::::{} for binding", JSON.toJSONString(roles), k);
      rsrMappings.get(clientUuidMappings.get(k)).add(roles);
    });
  }

  /**
   * @param clientId
   * @param roleName
   * @return
   */
  public List<ScopePermissionRepresentation> listPermissionsOfRoleByInit(String clientId, String roleName) {
    List<ScopePermissionRepresentation> ps = Lists.newArrayList();
    PolicyRepresentation role = loadRolePolicyMapByInit(clientId, roleName);

    if (!Objects.isNull(role)) {
      List<String> pIds = Lists.newArrayList(role.getId());
      ps = super.loadPermReps(clientId).stream()
              .filter(item -> {
                ScopePermissionResource res = super.loadPermRes(clientId, item.getId());
                List<String> policyIds = res.associatedPolicies().stream()
                        .map(PolicyRepresentation::getId)
                        .collect(Collectors.toList());
                for (String id : policyIds) {
                  if (pIds.contains(id)) {
                    return true;
                  }
                }
                return false;
              })
              .collect(Collectors.toList());
    }

    return ps;
  }
}
