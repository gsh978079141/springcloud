package com.gsh.springcloud.account.keycloak.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.gsh.springcloud.account.config.support.keycloak.KeycloakAdminProperties;
import com.gsh.springcloud.account.constant.AuthorizationConstants;
import com.gsh.springcloud.account.constant.RoleConstants;
import com.gsh.springcloud.account.converter.PermissionConverter;
import com.gsh.springcloud.account.converter.ResourceConverter;
import com.gsh.springcloud.account.converter.RoleConverter;
import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.dto.RoleDto;
import com.gsh.springcloud.account.exception.*;
import com.gsh.springcloud.account.keycloak.KeycloakHandlerService;
import com.gsh.springcloud.account.request.RoleCreateReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.ScopePermissionResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jun
 */
@Slf4j
@Service
public class KeycloakHandlerServiceImpl implements KeycloakHandlerService {

  private final String DEFAULT_RESOURCE = "Default Resource";

  private final String ROLE_POLICY_PREFIX = "pol.role.";

  @Resource
  Keycloak keycloak;

  @Resource
  KeycloakAdminProperties properties;

  @Resource
  ResourceConverter resourceConverter;

  @Resource
  PermissionConverter permissionConverter;

  @Resource
  RoleConverter roleConverter;


  @Override
  public ClientAuthorizationDataDto getClientDataOfCurRealm(String clientId) {
    RealmRepresentation realm = getCurRealmResource().toRepresentation();
    return getClientData(realm.getRealm(), clientId);
  }


  @Override
  public ClientAuthorizationDataDto getClientData(String realm, String clientId) {
    ClientResource cr = getClientResource(realm, clientId);
    log.info(">>>>>>>>>>>>>>>>>>begin load total data of client[{}]<<<<<<<<<<<<<<<<<<<<", clientId);
    // 加载client下所有resource
    List<ResourceDto> resources = listAllResources(cr);
    // 加载client下所有permission
    List<PermissionDto> permissions = listAllPermissions(cr);
    // 获取 resource -> permissions 映射关系
    Map<String, List<PermissionDto>> resourcePermissionsMap = getResourcePermissionsMap(cr, resources, permissions);
    // 加载client下所有role
    List<RoleDto> roles = listAllRoles(cr);
    // 获取 role -> permissions 映射关系
    Map<String, List<PermissionDto>> rolePermissionsMap = getRolePermissionsMap(cr, roles, permissions);

    return ClientAuthorizationDataDto.builder()
            .realm(realm)
            .clientId(clientId)
            .resources(resources)
            .permissions(permissions)
            .roles(roles)
            .resourcePermissionsMap(resourcePermissionsMap)
            .rolePermissionsMap(rolePermissionsMap)
            .build();
  }


  @Override
  public ClientAuthorizationDataDto getClientData(String realm, String clientId, Set<String> targetRoles) {
    checkAndRefreshToken();
    ClientResource cr = getClientResource(realm, clientId);
    log.info(">>>>>>>>>>>>>>>>>>begin load total data of client[{}]<<<<<<<<<<<<<<<<<<<<", clientId);
    // 加载client下所有permission
    List<PermissionDto> permissions = listAllPermissions(cr);
    // 加载client下所有resource
    List<ResourceDto> resources = listAllResources(cr);
    // 获取 resource -> permissions 映射关系
    Map<String, List<PermissionDto>> resourcePermissionsMap = getResourcePermissionsMap(cr, resources, permissions);
    List<RoleDto> roles = listAllRoles(cr).stream()
            .filter(roleDto -> targetRoles.contains(roleDto.getName())).collect(Collectors.toList());
    // 获取 role -> permissions 映射关系
    Map<String, List<PermissionDto>> rolePermissionsMap = getRolePermissionsMap(cr, roles, permissions);
    return ClientAuthorizationDataDto.builder()
            .realm(realm)
            .clientId(clientId)
            .resources(resources)
            .permissions(permissions)
            .roles(roles)
            .resourcePermissionsMap(resourcePermissionsMap)
            .rolePermissionsMap(rolePermissionsMap)
            .build();
  }


  @Override
  public void importClientAuthorizationData(ClientAuthorizationDataDto data) {
    checkAndRefreshToken();
    String realmName = data.getRealm();
    String clientId = data.getClientId();
    boolean existRealm = isRealmExisted(realmName);
    if (!existRealm) {
      throw new RealmNotFoundException(realmName);
    }
    List<ClientRepresentation> clients = keycloak.realm(realmName).clients().findByClientId(clientId);
    if (clients.isEmpty()) {
      throw new ClientNotFoundException(clientId);
    }

    ClientResource clientResource = keycloak.realm(realmName).clients().get(clients.get(0).getId());

    // 1. 检查角色和策略数据是否存在，不存在则导入
    Set<String> existingRoles = clientResource.roles().list().stream()
            .map(RoleRepresentation::getName).collect(Collectors.toSet());

    Set<String> existingPolicies = clientResource.authorization().policies().policies()
            .stream().map(PolicyRepresentation::getName)
            .filter(name -> name.startsWith(ROLE_POLICY_PREFIX)).collect(Collectors.toSet());
    log.info("begin import roles data....");
    data.getRoles().forEach(roleDto -> {
      if (!existingRoles.contains(roleDto.getName())) {
        createRole(clientResource, roleDto);
        log.info("create client role:[{}]", roleDto.getName());
      } else {
        // TODO 判断是否需要修改
        log.info("role:[{}] is existed!", roleDto.getName());
      }

      String policyName = ROLE_POLICY_PREFIX.concat(roleDto.getName());
      if (!existingPolicies.contains(policyName)) {
        RolePolicyRepresentation rpr = new RolePolicyRepresentation();
        rpr.setName(policyName);
        rpr.addClientRole(clientId, roleDto.getName());
        rpr.setDescription(String.format("角色[%s]专用策略", roleDto.getName()));
        createRolePolicy(clientResource, rpr);
      } else {
        log.info("policy:[{}] is existed!", policyName);
      }
    });

    log.info("begin import resources data....");

    Map<String, ResourceRepresentation> existedResourceMap = clientResource.authorization().resources().resources()
            .stream().filter(r -> !r.getName().equals(DEFAULT_RESOURCE))
            .collect(Collectors.toMap(ResourceRepresentation::getName, Function.identity()));

    log.info("existed resources count: {} ", existedResourceMap.size());

    data.getResources().forEach(resourceDto -> {
      if (existedResourceMap.containsKey(resourceDto.getName())) {
        // TODO 检查是否需要更新
        log.info("resource[{}] is existed, don't need to create again", resourceDto.getName());
      } else {
        // 新增resource
        createResource(clientResource, resourceDto);
      }
    });

    Set<String> existedPermissions = clientResource.authorization().policies().policies()
            .stream().filter(i -> AuthorizationConstants.SCOPE.equals(i.getType()))
            .map(PolicyRepresentation::getName).collect(Collectors.toSet());

    // 获取每个权限对应的资源map
    Map<String, Set<String>> permissionResourceMap = Maps.newHashMap();
    data.getResourcePermissionsMap().forEach((resourceName, rPermissions) -> {
      rPermissions.forEach(p -> {
        if (permissionResourceMap.containsKey(p.getName())) {
          permissionResourceMap.get(p.getName()).add(resourceName);
        } else {
          permissionResourceMap.put(p.getName(), Sets.newHashSet(resourceName));
        }
      });
    });

    // 获取每个权限对应的资源policy列表
    Map<String, Set<String>> permissionPoliciesMap = Maps.newHashMap();
    data.getRolePermissionsMap().forEach((roleName, rPermissions) -> {
      rPermissions.forEach(p -> {
        String policyName = ROLE_POLICY_PREFIX + roleName;
        if (permissionPoliciesMap.containsKey(p.getName())) {
          permissionPoliciesMap.get(p.getName()).add(policyName);
        } else {
          permissionPoliciesMap.put(p.getName(), Sets.newHashSet(policyName));
        }
      });
    });

    data.getPermissions().forEach(permissionDto -> {
      if (existedPermissions.contains(permissionDto.getName())) {
        // TODO 判断是否需要更新
        log.info("permission[{}] is existed, don't need to create again", permissionDto.getName());
      } else {
        createPermission(clientResource, permissionDto,
                permissionResourceMap.getOrDefault(permissionDto.getName(), Sets.newHashSet()),
                permissionPoliciesMap.getOrDefault(permissionDto.getName(), Sets.newHashSet()));
      }
    });

  }

  @Override
  public List<RoleDto> listRolesOfUser(String clientId, String userId) {

    UserRepresentation user = getCurRealmResource().users().get(userId).toRepresentation();
    if (Objects.isNull(user)) {
      throw new UserNotFoundException();
    }
    ClientResource clientResource = getClientResourceOfCurRealm(clientId);
    String clientUUID = clientResource.toRepresentation().getId();
    List<RoleRepresentation> roleRepresentations = getCurRealmResource().users().get(userId)
            .roles().clientLevel(clientUUID).listEffective();
    if (roleRepresentations.isEmpty()) {
      return Lists.newArrayList();
    }
    List<RoleDto> roles = roleRepresentations.stream()
            .filter(r -> !RoleConstants.IGNORE_ROLE_NAMES.contains(r.getName()))
            .map(roleConverter::entity2dto)
            .collect(Collectors.toList());
    return roles;
  }

  private void createRole(ClientResource clientResource, RoleDto roleDto) {
    RoleRepresentation role = new RoleRepresentation();
    role.setName(roleDto.getName());
    role.setDescription(roleDto.getDescription());
    role.setClientRole(roleDto.getClientRole());
    role.setAttributes(roleDto.getAttributes());
    clientResource.roles().create(role);
    log.info("create client role:[{}]", roleDto.getName());
  }

  private void createRolePolicy(ClientResource clientResource, RolePolicyRepresentation rpr) {
    Response response = null;
    try {
      response = clientResource.authorization().policies().role().create(rpr);
      log.info("create client role_policy:[{}]", rpr.getName());
    } catch (Exception e) {
      throw new AccountException(AccountException.ExceptionEnum.CREATE_POLICY_ERROR);
    } finally {
      if (response != null) {
        try {
          response.close();
        } catch (Exception e) {
          log.error("client.close() Error", e);
        }
      }
    }
  }


  private void createResource(ClientResource clientResource, ResourceDto dto) {
    ResourceRepresentation resource = new ResourceRepresentation();
    resource.setName(dto.getName());
    resource.setDisplayName(dto.getDisplayName());
    resource.setType(dto.getType());
    resource.setAttributes(dto.getAttributes());
    resource.setOwnerManagedAccess(dto.getOwnerManagedAccess());
    resource.setUris(dto.getUris());
    Response response = null;
    try {
      response = clientResource.authorization().resources().create(resource);
      log.info("create resource [{}] , response status:{}, {}", dto.getName(),
              response.getStatus(), JSON.toJSONString(response));
    } catch (Exception e) {
      throw new AccountException(AccountException.ExceptionEnum.CREATE_RESOURCE_ERROR);
    } finally {
      if (response != null) {
        try {
          response.close();
        } catch (Exception e) {
          log.error("client.close() Error", e);
        }
      }
    }
  }

  private void createPermission(ClientResource clientResource, PermissionDto permissionDto, Set<String> resources, Set<String> policies) {
    ScopePermissionRepresentation p = new ScopePermissionRepresentation();
    p.setName(permissionDto.getName());
    p.setDescription(permissionDto.getDescription());
    p.setResources(resources);
    p.setPolicies(policies);
    p.setDecisionStrategy(DecisionStrategy.UNANIMOUS);
    Response response = null;
    try {
      response = clientResource.authorization().permissions().scope().create(p);
      log.info("create permission [{}] , response status:{}, {}", permissionDto.getName(),
              response.getStatus(), JSON.toJSONString(response));
    } catch (Exception e) {
      throw new AccountException(AccountException.ExceptionEnum.CREATE_PERMISSION_ERROR);
    } finally {
      if (response != null) {
        try {
          response.close();
        } catch (Exception e) {
          log.error("client.close() Error", e);
        }
      }
    }

  }

  private void checkAndRefreshToken() {
    // 所有keycloak相关请求在调用前都要调用该方法统一检查下当前token是否过期
    // TODO 检查当前token，过期则刷新
    keycloak.tokenManager().refreshToken().getToken();
  }

  private RealmResource getCurRealmResource() {
    return keycloak.realm(properties.getManagedRealm());
  }

  private ClientResource getClientResourceOfCurRealm(String clientId) {
    RealmResource curRealmResource = getCurRealmResource();
    List<ClientRepresentation> clients = curRealmResource.clients().findByClientId(clientId);
    if (clients.isEmpty()) {
      throw new ClientNotFoundException(clientId);
    }
    ClientResource cr = curRealmResource.clients().get(clients.get(0).getId());
    ClientRepresentation client = cr.toRepresentation();
    log.info("found client:{} in current realm", client.getClientId());
    return cr;
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
              ScopePermissionRepresentation permission = permissionResource.toRepresentation();
              PermissionDto permissionDto = permissionConverter.entity2dto(permission);
              permissions.add(permissionDto);
              log.info("load permission: {}", JSON.toJSONString(permissionDto));
              //permission
            });
    return permissions;
  }

  private List<RoleDto> listAllRoles(ClientResource cr) {
    return cr.roles().list().stream().filter(r -> !RoleConstants.IGNORE_ROLE_NAMES.contains(r.getName()))
            .map(roleConverter::entity2dto).collect(Collectors.toList());
  }

  private Map<String, List<PermissionDto>> getResourcePermissionsMap(
          ClientResource cr, List<ResourceDto> resources, List<PermissionDto> permissions) {
    Map<String, List<PermissionDto>> resourcePermissionsMap = Maps.newHashMapWithExpectedSize(64);
    // 建立resource和permission的映射关系
    resources.forEach(resource -> resourcePermissionsMap.put(resource.getName(), Lists.newArrayList()));

    // 加载client下所有permission
    permissions.forEach(permissionDto -> {
      ScopePermissionResource permissionResource = cr.authorization().permissions().scope()
              .findById(permissionDto.getId());
      List<ResourceRepresentation> pResources = permissionResource.resources();
      pResources.stream().filter(r -> !DEFAULT_RESOURCE.equals(r.getName())).forEach(r -> {
        if (resourcePermissionsMap.containsKey(r.getName())) {
          log.info("permission[{}] belong to resource[{}]",
                  permissionDto.getName(), r.getName());
          resourcePermissionsMap.get(r.getName()).add(permissionDto);
        } else {
          log.error("not found resource:{} for permission:{}", r.getName(), permissionDto.getName());
        }
      });
    });
    return resourcePermissionsMap;
  }

  private Map<String, List<PermissionDto>> getRolePermissionsMap(
          ClientResource cr, List<RoleDto> roles, List<PermissionDto> permissions) {
    Map<String, List<PermissionDto>> rolePermissionsMap = Maps.newHashMapWithExpectedSize(1024);
    roles.forEach(role -> {
      log.info("load role: {}", JSON.toJSONString(role));
      rolePermissionsMap.put(role.getName(), Lists.newArrayListWithCapacity(64));
    });
    permissions.forEach(permissionDto -> {
      ScopePermissionResource permissionResource = cr.authorization().permissions().scope()
              .findById(permissionDto.getId());
      List<PolicyRepresentation> associatedPolicies = permissionResource.associatedPolicies();
      associatedPolicies.stream().filter(p -> p.getName().startsWith(ROLE_POLICY_PREFIX)).forEach(p -> {
        String roleName = p.getName().replace(ROLE_POLICY_PREFIX, "");
        if (!rolePermissionsMap.containsKey(roleName)) {
          log.warn("not found role of policy:[{}]", p.getName());
          return;
        }
        rolePermissionsMap.get(roleName).add(permissionDto);
      });
    });
    rolePermissionsMap.forEach(
            (roleName, rPermissions) -> log.info("role[{}] has[{}] permissions", roleName, rPermissions.size()));
    return rolePermissionsMap;
  }

  private boolean isRealmExisted(String realmName) {
    return keycloak.realms().findAll().stream().anyMatch(t -> t.getRealm().equals(realmName));
  }

  @Override
  public RoleDto getRoleById(String clientId, String roleId) {
    RoleRepresentation rr = getClientResourceOfCurRealm(clientId).roles().list().stream()
            .filter(item -> item.getId().equals(roleId))
            .findFirst()
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    return roleConverter.entity2dto(rr);
  }

  @Override
  public void createRoleAndRolePolicy4Client(String clientId, RoleCreateReq req) {
    getClientResourceOfCurRealm(clientId).roles().create(roleConverter.req2entity(req));
    if (StringUtils.isAnyBlank(req.getName(), clientId)) {
      return;
    }
    RolePolicyRepresentation rpr = new RolePolicyRepresentation();
    rpr.setName(AuthorizationConstants.POLICY_PREFIX.concat(req.getName()));
    rpr.addClientRole(clientId, req.getName());
    getClientResourceOfCurRealm(clientId).authorization().policies().role().create(rpr);
  }

  @Override
  public Set<String> getExistingPolicies(String clientId) {
    return getClientResourceOfCurRealm(clientId).authorization().policies().policies()
            .stream().map(PolicyRepresentation::getName)
            .filter(name -> name.startsWith(ROLE_POLICY_PREFIX)).collect(Collectors.toSet());
  }

  private PolicyRepresentation getPolicyWithRoleName(String clientId, String roleName) {
    return getClientResourceOfCurRealm(clientId).authorization().policies().policies().stream()
            .filter(item -> AuthorizationConstants.ROLE.equals(item.getType()))
            .filter(item -> item.getName().equals(AuthorizationConstants.POLICY_PREFIX.concat(roleName)))
            .findFirst().orElse(null);
  }

  @Override
  public void updateRole(String clientId, RoleDto rr) {
    getClientResourceOfCurRealm(clientId).roles().get(rr.getName()).update(roleConverter.dto2entity(rr));
  }

  @Override
  public void deleteRole(String clientId, RoleDto rr) {
    if (checkRoleInUse(clientId, rr.getName())) {
      throw new RoleInUseException();
    }
    getClientResourceOfCurRealm(clientId).roles().deleteRole(rr.getName());
  }


  @Override
  public List<String> bindingRoleAndPermissions(String clientId, RoleDto role, List<PermissionDto> permissions, List<String> permissionIds) {

    Map<String, ScopePermissionRepresentation> permMap = permissionConverter.dto2entity(permissions).stream()
            .filter(item -> permissionIds.contains(item.getId()))
            .collect(Collectors.toMap(ScopePermissionRepresentation::getId, Function.identity()));

    PolicyRepresentation rpr = getPolicyWithRoleName(clientId, role.getName());

    List<String> pList = new ArrayList<>();

    permissionIds.forEach(permissionId -> {
              ScopePermissionResource permissionResource = getClientResourceOfCurRealm(clientId).authorization().permissions().scope()
                      .findById(permissionId);
              List<PolicyRepresentation> associatedPolicies = permissionResource.associatedPolicies();
              associatedPolicies.add(rpr);
              ScopePermissionRepresentation representation = permMap.get(permissionId);
              log.info("roleName: {} binding permissionIds :{} ", role.getName(), permissionIds, representation.getName());
              pList.add(representation.getName());
              representation.setPolicies(associatedPolicies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
              permissionResource.update(representation);
            }
    );
    return pList;
  }

  @Override
  public List<String> bindingRoleAndPermissions(String clientId, RoleDto role, List<ScopePermissionRepresentation> permissions) {

    PolicyRepresentation rpr = getPolicyWithRoleName(clientId, role.getName());

    List<String> pList = new ArrayList<>();

    permissions.forEach(permission -> {
              ScopePermissionResource permissionResource = getClientResourceOfCurRealm(clientId).authorization().permissions().scope()
                      .findById(permission.getId());
              List<PolicyRepresentation> associatedPolicies = permissionResource.associatedPolicies();
              associatedPolicies.add(rpr);
              ScopePermissionRepresentation representation = permission;
              log.info("roleName: {} binding permissions :{} ", role.getName(), representation.getName());
              pList.add(representation.getName());
              representation.setPolicies(associatedPolicies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
              permissionResource.update(representation);
            }
    );
    return pList;
  }

  @Override
  public void unbindingRoleAndPermissions(String clientId, RoleDto role, List<PermissionDto> permissionDtos, List<String> permissionIds) {

    Map<String, ScopePermissionRepresentation> permMap = permissionConverter.dto2entity(permissionDtos).stream()
            .filter(item -> permissionIds.contains(item.getId()))
            .collect(Collectors.toMap(ScopePermissionRepresentation::getId, Function.identity()));
    PolicyRepresentation rpr = getPolicyWithRoleName(clientId, role.getName());
    if (Objects.isNull(rpr)) {
      return;
    }
    permissionIds.forEach(permissionId -> {
              ScopePermissionResource permissionResource = getClientResourceOfCurRealm(clientId).authorization().permissions().scope()
                      .findById(permissionId);
              List<PolicyRepresentation> associatedPolicies = permissionResource.associatedPolicies();
              associatedPolicies.remove(rpr);
              ScopePermissionRepresentation representation = permMap.get(permissionId);
              log.info("roleName: {} unbinding permissionIds :{} ", role.getName(), permissionIds, representation.getName());
              representation.setPolicies(associatedPolicies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
              permissionResource.update(representation);
            }
    );
  }

  @Override
  public void unbindingRoleAndPermissions(String clientId, RoleDto role, List<ScopePermissionRepresentation> permissions) {

    PolicyRepresentation rpr = getPolicyWithRoleName(clientId, role.getName());
    if (Objects.isNull(rpr)) {
      return;
    }
    permissions.forEach(permission -> {
              ScopePermissionResource permissionResource = getClientResourceOfCurRealm(clientId).authorization().permissions().scope()
                      .findById(permission.getId());
              List<PolicyRepresentation> associatedPolicies = permissionResource.associatedPolicies();
              associatedPolicies.remove(rpr);
              ScopePermissionRepresentation representation = permission;
              log.info("roleName: {} unbinding permission :{} ", role.getName(), representation.getName());
              representation.setPolicies(associatedPolicies.stream().map(PolicyRepresentation::getId).collect(Collectors.toSet()));
              permissionResource.update(representation);
            }
    );
  }

  @Override
  public boolean checkRoleInUse(String clientId, String roleName) {
    return getClientResourceOfCurRealm(clientId).roles().get(roleName).getRoleUserMembers(0, 1).size() > 0;
  }

  @Override
  public RoleDto getRoleByName(String clientId, String roleName) {
    RoleRepresentation r = getClientResourceOfCurRealm(clientId).roles().get(roleName).toRepresentation();
    return roleConverter.entity2dto(r);
  }
}
