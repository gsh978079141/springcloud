package com.gsh.springcloud.gateway.keycloak.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.gsh.springcloud.gateway.config.keycloak.KeycloakAdminProperties;
import com.gsh.springcloud.gateway.constant.AuthorizationConstants;
import com.gsh.springcloud.gateway.constant.RoleConstants;
import com.gsh.springcloud.gateway.domain.*;
import com.gsh.springcloud.gateway.domain.converter.ResourceConverter;
import com.gsh.springcloud.gateway.domain.converter.RoleConverter;
import com.gsh.springcloud.gateway.exception.ClientNotFoundException;
import com.gsh.springcloud.gateway.exception.RealmNotFoundException;
import com.gsh.springcloud.gateway.keycloak.KeycloakHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ScopePermissionResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gsh
 */
@Slf4j
@Service
public class KeycloakHandlerServiceImpl implements KeycloakHandlerService, InitializingBean {

  private final String DEFAULT_RESOURCE = "Default Resource";

  private final String ROLE_POLICY_PREFIX = "pol.role.";


  @Resource
  private KeycloakAdminProperties keycloakAdminProperties;

  @Resource
  private Keycloak keycloak;

  @Resource
  private ResourceConverter resourceConverter;


  @Resource
  private RoleConverter roleConverter;


  /**
   * keycloak里当前client对象操作的句柄，
   * keycloak里 每个ClientRepresentation对象有clientId和id两个属性
   * clientId为业务id, id 是keycloak自动生成的随机ID
   * ClientResource 只能根据realm, id获取
   * 必须先根据id在keycloak查出ClientRepresentation对象，然后取到id
   * 所以为了避免每次进行client相关查询都要获取ClientResource，在类初始化完后将ClientResource对象保存到实例的全局变量
   */
  private ClientResource cr;

  private String realm;

  private String clientId;


  @Override
  public void afterPropertiesSet() throws Exception {
    try {
      this.realm = keycloakAdminProperties.getManagedRealm();
      this.clientId = keycloakAdminProperties.getManagedClientId();

      List<RealmRepresentation> realms = keycloak.realms().findAll();
      boolean isRealmExisted = realms.stream().anyMatch(r -> r.getRealm().equals(realm));
      if (!isRealmExisted) {
        throw new RealmNotFoundException(realm);
      }
      List<ClientRepresentation> clients = keycloak.realm(realm).clients().findByClientId(clientId);
      if (clients.isEmpty()) {
        throw new ClientNotFoundException(clientId);
      }
      ClientResource cr = keycloak.realm(realm).clients().get(clients.get(0).getId());
      log.info("init ClientResource of KeycloakHandlerServiceImpl success,realm:{}, clientId:{}", realm, clientId);
      this.cr = cr;
    } catch (Exception e) {
      log.error("init ClientResource of KeycloakHandlerServiceImpl error, realm:{}, clientId:{}", realm, clientId, e);
      throw e;
    }
  }


  @Override
  public ClientAuthorizationDataDto getClientData() {

    log.info(">>>>>>>>>>>>>>>>>>load total data of client[{}] begin<<<<<<<<<<<<<<<<<<<<", clientId);
    // 加载client下所有permission
    List<PermissionDto> permissions = listAllPermissions(cr);
    // 加载client下所有role
    List<RoleDto> roles = listAllRoles(cr);
    // 获取 role -> permissions 映射关系
    Map<String, Set<String>> rolePermissionsMap = getRolePermissionsMap(cr, roles, permissions);
    log.info(">>>>>>>>>>>>>>>>>>load total data of client[{}] end<<<<<<<<<<<<<<<<<<<<", clientId);
    return ClientAuthorizationDataDto.builder()
            .permissions(permissions)
            .roles(roles)
            .rolePermissionsMap(rolePermissionsMap)
            .build();
  }

  @Override
  public List<RoleDto> listAllRoles() {
    return listAllRoles(cr);
  }

  @Override
  public Map<String, Set<String>> getRolePermissionsMap(List<RoleDto> roles, List<PermissionDto> permissions) {
    return getRolePermissionsMap(cr, roles, permissions);
  }

  @Override
  public Optional<UserPrincipal> verifyToken(String token) {
    try {
      //1、设置client配置信息
      AdapterConfig adapterConfig = new AdapterConfig();
      //realm name
      adapterConfig.setRealm(keycloakAdminProperties.getManagedRealm());
      //client_id
      adapterConfig.setResource(keycloakAdminProperties.getLoginClientId());
      //认证中心keycloak地址
      adapterConfig.setAuthServerUrl(keycloakAdminProperties.getServerUrl());
      //访问https接口时，禁用证书检查。
      adapterConfig.setDisableTrustManager(true);
      //2、根据client配置信息构建KeycloakDeployment对象
      KeycloakDeployment deployment = KeycloakDeploymentBuilder.build(adapterConfig);
      //3、执行token签名验证和有效性检查（不通过会抛异常）
      AccessToken accessToken = AdapterTokenVerifier.verifyToken(token, deployment);
      AccessToken.Access access = accessToken.getResourceAccess(keycloakAdminProperties.getManagedClientId());
      Set<String> roles = Objects.nonNull(access) ? access.getRoles() : Sets.newHashSet();
      UserPrincipal user = UserPrincipal.builder()
              .id(accessToken.getSubject())
              .username(accessToken.getPreferredUsername())
              .givenName(accessToken.getGivenName())
              .roles(roles)
              .build();
      return Optional.of(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }


  @Override
  public Optional<UserDto> findUser(String username) {
    List<UserRepresentation> users = keycloak.realm(realm).users().search(username);
    if (users.isEmpty()) {
      return Optional.empty();
    }
    UserRepresentation user = users.get(0);
    log.info("find user: {}", JSON.toJSONString(user));
    Map<String, String> attributeMap = Maps.newHashMap();
    if (!user.getAttributes().isEmpty()) {
      user.getAttributes().forEach((key, values) -> attributeMap.put(key, values.get(0)));
    }
    UserDto userDto = UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .attributes(attributeMap)
            .build();
    return Optional.of(userDto);
  }


  private void checkAndRefreshToken() {
    // 所有keycloak相关请求在调用前都要调用该方法统一检查下当前token是否过期
    // TODO 检查当前token，过期则刷新
    keycloak.tokenManager().refreshToken().getToken();
  }


  private ClientResource getClientResource(String realm, String clientId) {
    List<RealmRepresentation> realms = keycloak.realms().findAll();
    boolean isRealmExisted = realms.stream().anyMatch(r -> r.getRealm().equals(realm));
    if (!isRealmExisted) {
      throw new RealmNotFoundException(realm);
    }
    List<ClientRepresentation> clients = keycloak.realm(realm).clients().findByClientId(clientId);
    if (clients.isEmpty()) {
      throw new ClientNotFoundException(clientId);
    }
    ClientResource cr = keycloak.realm(realm).clients().get(clients.get(0).getId());
    ClientRepresentation client = cr.toRepresentation();
    log.info("found client:{} of realm[{}]", client.getClientId(), realm);
    return cr;
  }


  private List<ResourceDto> listAllResources(ClientResource cr) {
    // 加载client下所有resource
    List<ResourceDto> resources = cr.authorization().resources().resources()
            .stream().filter(r -> !DEFAULT_RESOURCE.equals(r.getName()))
            .map(resourceConverter::entity2dto).collect(Collectors.toList());
    log.info("Has loaded [{}] resources...", resources.size());
    return resources;
  }

  private List<PermissionDto> listAllPermissions(ClientResource cr) {
    List<PermissionDto> permissions = Lists.newArrayListWithCapacity(96);
    cr.authorization().policies().policies().stream()
            // 类型为scope 是 permission
            .filter(i -> AuthorizationConstants.SCOPE.equals(i.getType()))
            .forEach(item -> {
              ScopePermissionResource permissionResource = cr.authorization().permissions().scope()
                      .findById(item.getId());
//              ScopePermissionRepresentation permission = permissionResource.toRepresentation();
//              PermissionDto permissionDto = permissionConverter.convert(permission);
              PermissionDto permissionDto = PermissionDto.builder()
                      .id(item.getId())
                      .name(item.getName())
                      .description(item.getDescription())
                      .decisionStrategy(item.getDecisionStrategy().name())
                      .type(item.getType())
                      .logic(item.getLogic().name())
                      .build();
              permissions.add(permissionDto);
              log.info("load permission: {}", JSON.toJSONString(permissionDto));
            });
    return permissions;
  }

  private List<RoleDto> listAllRoles(ClientResource cr) {
    return cr.roles().list().stream().filter(r -> !RoleConstants.IGNORE_ROLE_NAMES.contains(r.getName()))
            .map(roleConverter::convert).collect(Collectors.toList());
  }


  private Map<String, Set<String>> getRolePermissionsMap(
          ClientResource cr, List<RoleDto> roles, List<PermissionDto> permissions) {
    Set<String> roleSet = roles.stream().map(RoleDto::getName).collect(Collectors.toSet());
    return getRolePermissionsMap(cr, roleSet, permissions);
  }

  private Map<String, Set<String>> getRolePermissionsMap(
          ClientResource cr, Set<String> roles, List<PermissionDto> permissions) {
    Map<String, Set<String>> rolePermissionsMap = Maps.newHashMapWithExpectedSize(1024);
    roles.forEach(role -> {
      rolePermissionsMap.put(role, Sets.newHashSet());
    });
    permissions.forEach(permissionDto -> {
      // 查询权限关联的策略列表
      List<PolicyRepresentation> associatedPolicies = getAssociatedPolicies(cr, permissionDto);
      associatedPolicies.stream()
              .filter(p -> p.getName().startsWith(ROLE_POLICY_PREFIX))
              .forEach(p -> {
                String roleName = p.getName().replace(ROLE_POLICY_PREFIX, "");
                if (rolePermissionsMap.containsKey(roleName)) {
                  rolePermissionsMap.get(roleName).add(permissionDto.getName());
                }
              });
    });
    rolePermissionsMap.forEach(
            (roleName, rPermissions) -> log.info("role[{}] has[{}] permissions", roleName, rPermissions.size()));
    return rolePermissionsMap;
  }

  private List<PolicyRepresentation> getAssociatedPolicies(ClientResource cr, PermissionDto permission) {
    long t1 = System.currentTimeMillis();
    List<PolicyRepresentation> associatedPolicies = cr.authorization().permissions().scope()
            .findById(permission.getId()).associatedPolicies();
    long t2 = System.currentTimeMillis();
    log.debug("find [{}] associatedPolicies of permission[{}], cost [{}] ms ",
            associatedPolicies.size(), permission.getName(), t2 - t1);
    return associatedPolicies;

  }


}
