package com.gsh.springcloud.account.keycloak.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gsh.springcloud.account.converter.ResourceConverter;
import com.gsh.springcloud.account.dto.*;
import com.gsh.springcloud.account.keycloak.KeycloakCache;
import com.gsh.springcloud.account.keycloak.KeycloakCacheManager;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


/**
 * @author jun
 */
public class KeycloakMemoryCacheImpl implements KeycloakCacheManager, KeycloakCache {

  private static ReadWriteLock lock = new ReentrantReadWriteLock();
  /**
   * clientId -> resources
   * 业务操作不会更新resources，只有在新功能上线时才需要更新
   */
  private static Map<String, List<ResourceDto>> CLIENT_RESOURCES_MAP = Maps.newConcurrentMap();
  /**
   * clientId -> permissions
   * 业务操作不会更新permissions，只有在新功能上线时才需要更新
   */
  private static Map<String, List<PermissionDto>> CLIENT_PERMISSIONS_MAP = Maps.newConcurrentMap();
  private static Map<String, Map<String, List<PermissionDto>>> CLIENT_RESOURCE_PERMISSIONS_MAP = Maps.newConcurrentMap();
  /**
   * clientId -> roles
   * 正常业务操作会更新role，需要注意更新策略
   */
  private static Map<String, List<RoleDto>> CLIENT_ROLE_MAP = Maps.newConcurrentMap();
  private static Map<String, Map<String, List<PermissionDto>>> CLIENT_ROLES_PERMISSIONS_MAP = Maps.newConcurrentMap();

  /**
   * clientId -> roleName-> resources
   * 正常业务操作会更新，需要注意更新策略
   */
  private static Map<String, Map<String, List<ResourceDto>>> CLIENT_ROLES_RESOURCE_MAP = Maps.newConcurrentMap();

  @Resource
  private ResourceConverter resourceConverter;

  private KeycloakMemoryCacheImpl() {

  }

  public static KeycloakMemoryCacheImpl getInstance() {
    return KeycloakMemoryCacheBuilder.instance;
  }

  @Override
  public void refresh(Map<String, ClientAuthorizationDataDto> clientAuthorizationDataMap) {
    lock.writeLock().lock();
    try {
      clientAuthorizationDataMap.forEach((clientId, data) -> {
        CLIENT_ROLE_MAP.clear();
        CLIENT_ROLE_MAP.put(clientId, data.getRoles());
        CLIENT_RESOURCES_MAP.clear();
        CLIENT_RESOURCES_MAP.put(clientId, data.getResources());
        CLIENT_PERMISSIONS_MAP.clear();
        CLIENT_PERMISSIONS_MAP.put(clientId, data.getPermissions());
        CLIENT_RESOURCE_PERMISSIONS_MAP.clear();
        CLIENT_RESOURCE_PERMISSIONS_MAP.put(clientId, data.getResourcePermissionsMap());
        CLIENT_ROLES_PERMISSIONS_MAP.clear();
        CLIENT_ROLES_PERMISSIONS_MAP.put(clientId, data.getRolePermissionsMap());
        CLIENT_ROLES_RESOURCE_MAP.clear();
        CLIENT_ROLES_RESOURCE_MAP.put(clientId, getRoleResouecesMap(data.getResources(), data.getRolePermissionsMap(), data.getResourcePermissionsMap()));
      });
    } finally {
      lock.writeLock().unlock();
    }
  }

  //key rolename->ResourceDtos
  public Map<String, List<ResourceDto>> getRoleResouecesMap(List<ResourceDto> resources,
                                                            Map<String, List<PermissionDto>> rolePermissionsMap,
                                                            Map<String, List<PermissionDto>> resourcePermissionsMap) {

    Map<String, ResourceDto> resourceDtoMap = resources.stream().collect
            (Collectors.toMap(ResourceDto::getName, ResourceDto -> ResourceDto));

    Map<String, List<ResourceDto>> roleResourceMap = Maps.newConcurrentMap();

    rolePermissionsMap.forEach((k, v) -> v.forEach(permissionDto -> {// permissionDto-->permissions   PermissionDto
      List<ResourceDto> resourceDtos = roleResourceMap.getOrDefault(k, Lists.newArrayList());//管理员
      Set<String> existResource = resourceDtos.stream().map(ResourceDto::getName).collect(Collectors.toSet());//员工管理
      resourcePermissionsMap.forEach((key, value) -> value.forEach(pDto -> { // pDto-->resources 里面的   PermissionDto
        if (permissionDto.getName().equals(pDto.getName())) {//permisson equals add resource to resources list
          if (!existResource.contains(key)) {
            resourceDtos.add(resourceDtoMap.get(key));//管理员，<员工管理>
          }
        }
      }));
      roleResourceMap.put(k, resourceDtos);
    }));
    return roleResourceMap;
  }


  @Override
  public List<ResourceDto> listAllResources(String clientId) {
    lock.readLock().lock();
    try {
      List<ResourceDto> resources = CLIENT_RESOURCES_MAP.getOrDefault(clientId, Collections.emptyList());
      return JSON.parseArray(JSON.toJSONString(resources), ResourceDto.class);
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public List<PermissionDto> listAllPermissions(String clientId) {
    lock.readLock().lock();
    try {
      List<PermissionDto> permissions = CLIENT_PERMISSIONS_MAP.getOrDefault(clientId, Collections.emptyList());
      return JSON.parseArray(JSON.toJSONString(permissions), PermissionDto.class);
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public Map<String, List<ResourceDto>> getResourcesOfRoleMap(String clientId, Set<String> roleNames) {
    lock.readLock().lock();
    try {
      Map<String, List<ResourceDto>> resourceDtoMap = Maps.newHashMap();
      Map<String, List<ResourceDto>> roleResourcesMap = CLIENT_ROLES_RESOURCE_MAP.getOrDefault(clientId, Maps.newHashMap());
      roleNames.forEach(roleName -> {
        List<ResourceDto> resourceDtos = roleResourcesMap.getOrDefault(roleName, Lists.newArrayList());
        resourceDtoMap.put(roleName, resourceDtos);
      });
      return resourceDtoMap;
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public List<ResourceDetailDto> listResourceDetailsByRoleNames(String clientId, Set<String> roleNames) {
    lock.readLock().lock();
    try {
      List<ResourceDetailDto> resourceDetailDtos = Lists.newArrayList();
      Map<String, List<ResourceDto>> roleResourcesMap = CLIENT_ROLES_RESOURCE_MAP.getOrDefault(clientId, Maps.newHashMap());
      Map<String, List<PermissionDto>> rolePermissionsMap = CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newHashMap());
      Map<String, List<PermissionDto>> resourcePermissionsMap = CLIENT_RESOURCE_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newHashMap());
      // 根据 以上集合，按照roleNames过滤，得到最终的 List<ResourceDetailDto>
      roleNames.forEach(roleName -> {
        List<ResourceDto> roleResourceDtos = roleResourcesMap.getOrDefault(roleName, Lists.newArrayList());
        List<PermissionDto> rolePermissionDtos = rolePermissionsMap.getOrDefault(roleName, Lists.newArrayList());
        roleResourceDtos.forEach(resourceDto -> {
          List<PermissionDto> resourcePermissionDtos = resourcePermissionsMap.getOrDefault(resourceDto.getName(), Lists.newArrayList());
          resourcePermissionDtos.retainAll(rolePermissionDtos);
          ResourceDetailDto resourceDetailDto = resourceConverter.dto2detailDto(resourceDto);
          resourceDetailDto.setAssociatedPermissions(resourcePermissionDtos);
          resourceDetailDtos.add(resourceDetailDto);
        });
      });
      return resourceDetailDtos;
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public List<RoleDto> listAllRoles(String clientId) {
    lock.readLock().lock();
    try {
      List<RoleDto> roles = CLIENT_ROLE_MAP.getOrDefault(clientId, Collections.emptyList());
      return JSON.parseArray(JSON.toJSONString(roles), RoleDto.class);
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public boolean containsRoles(String clientId, Set<String> roleNames) {
    lock.readLock().lock();
    try {
      return CLIENT_ROLE_MAP.getOrDefault(clientId, Collections.emptyList())
              .stream().anyMatch(r -> roleNames.contains(r.getName()));
    } finally {
      lock.readLock().unlock();
    }

  }

  @Override
  public List<PermissionDto> listPermissionsOfRoles(String clientId, Set<String> roleNames) {
    if (CollectionUtils.isEmpty(roleNames)) {
      return Lists.newArrayList();
    }
    lock.readLock().lock();
    //key rolename   value PermissionDto
    Map<String, List<PermissionDto>> rolePermissionMap = CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(clientId, Collections.EMPTY_MAP);
    Map<String, PermissionDto> permissionMap = Maps.newHashMap();
    try {
      for (String roleName : roleNames) {
        List<PermissionDto> permissions = rolePermissionMap.get(roleName);
        if (CollectionUtils.isNotEmpty(permissions)) {
          permissions.forEach(p -> permissionMap.put(p.getName(), p));
        }
      }
      return new ArrayList<>(permissionMap.values());
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public List<RoleDto> listRolesByNames(String clientId, Set<String> roleNames) {
    lock.readLock().lock();
    try {
      return CLIENT_ROLE_MAP.getOrDefault(clientId, Collections.emptyList())
              .stream().filter(item -> roleNames.contains(item.getName()))
              .collect(Collectors.toList());
    } finally {
      lock.readLock().unlock();
    }
  }


  @Override
  public boolean hasPermissionInRoles(String cilentId, Set<String> roleNames, String permission) {
    lock.readLock().lock();
    //key rolename   value PermissionDto
    Map<String, List<PermissionDto>> rolePermissionMap = CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(cilentId, Collections.EMPTY_MAP);
    try {
      for (String role : roleNames) {
        List<PermissionDto> permissionDtos = rolePermissionMap.get(roleNames);
        for (PermissionDto dto : permissionDtos) {
          if (dto.getName().equals(permission)) {
            return true;
          }
        }
      }
      return false;
    } finally {
      lock.readLock().unlock();
    }
  }

  private static class KeycloakMemoryCacheBuilder {
    private static final KeycloakMemoryCacheImpl instance = new KeycloakMemoryCacheImpl();
  }

  @Override
  public Map<String, List<PermissionDto>> listAllRolesPermissions(String clientId) {
    lock.readLock().lock();
    try {
      return CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public void updateRole(String clientId, RoleDto role) {

  }

  @Override
  public void updateRolePermissions(String clientId, String roleName, Set<String> permissions) {

  }

  @Override
  public Map<String, List<ResourceDto>> listAllRoleResources(String clientId) {
    lock.readLock().lock();
    try {
      return CLIENT_ROLES_RESOURCE_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
    } finally {
      lock.readLock().unlock();
    }

  }

  @Override
  public Map<String, List<PermissionDto>> listAllResourcePermissions(String clientId) {
    lock.readLock().lock();
    try {
      return CLIENT_RESOURCE_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
    } finally {
      lock.readLock().unlock();
    }
  }

  public void addRole(String clientId, RoleDto role, Set<String> permissions) {
    lock.writeLock().lock();
    try {
      List<RoleDto> roles = CLIENT_ROLE_MAP.getOrDefault(clientId, Collections.emptyList());
      Map<String, List<PermissionDto>> rolePermissionMap = CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
      roles.add(role);
      //update role  cache
      CLIENT_ROLE_MAP.put(clientId, roles);

      Map<String, PermissionDto> permissionDtoMap = CLIENT_PERMISSIONS_MAP.getOrDefault(clientId, Collections.emptyList()).stream().collect
              (Collectors.toMap(PermissionDto::getName, PermissionDto -> PermissionDto));
      List<PermissionDto> pList = Lists.newArrayList();
      for (String pName : permissions) {
        pList.add(permissionDtoMap.get(pName));
      }
      rolePermissionMap.put(role.getName(), pList);
      //update role permission cache
      CLIENT_ROLES_PERMISSIONS_MAP.put(clientId, rolePermissionMap);
      //update CLIENT_ROLES_RESOURCE_MAP;
      Map<String, List<ResourceDto>> resouecesRoleMap = getRoleResouecesMap(listAllResources(clientId),
              rolePermissionMap,
              listAllResourcePermissions(clientId));
      CLIENT_ROLES_RESOURCE_MAP.put(clientId, resouecesRoleMap);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void updateRole(String clientId, RoleDto role, Set<String> permissions) {
    lock.writeLock().lock();
    try {
      //load role cache
      List<RoleDto> roles = CLIENT_ROLE_MAP.getOrDefault(clientId, Collections.emptyList());
      //load permission cache : key->roleName
      Map<String, List<PermissionDto>> rolePermissionMap = CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
      List<RoleDto> roleUpdate = Lists.newArrayList();
      roleUpdate.add(role);

      List<RoleDto> roleDtos = roles.stream().filter(item -> !item.getId().equals(role.getId())).collect(Collectors.toList());
      // add new updated role to cache
      roleDtos.addAll(roleUpdate);
      CLIENT_ROLE_MAP.put(clientId, roleDtos);
      List<PermissionDto> allPermissions = CLIENT_PERMISSIONS_MAP.getOrDefault(clientId, Collections.emptyList());
      Map<String, PermissionDto> permissionDtoMap = allPermissions.stream().collect
              (Collectors.toMap(PermissionDto::getName, PermissionDto -> PermissionDto));

      if (!permissions.isEmpty()) {
        rolePermissionMap.forEach((key, value) -> {
          if (key.equals(role.getName())) {
            List<PermissionDto> permissionDtoList = rolePermissionMap.get(role.getName());
            Map<String, PermissionDto> map = permissionDtoList.stream().collect
                    (Collectors.toMap(PermissionDto::getName, PermissionDto -> PermissionDto));
            permissions.forEach(n -> {
              if (!map.containsKey(n)) {
                permissionDtoList.add(permissionDtoMap.get(n));
              }
            });
          }
        });
        // add new updated role-permission to cache
        CLIENT_ROLES_PERMISSIONS_MAP.put(clientId, rolePermissionMap);
      }
      //update CLIENT_ROLES_RESOURCE_MAP;
      Map<String, List<ResourceDto>> resouecesRoleMap = getRoleResouecesMap(listAllResources(clientId),
              rolePermissionMap,
              listAllResourcePermissions(clientId));
      CLIENT_ROLES_RESOURCE_MAP.put(clientId, resouecesRoleMap);
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Override
  public void deleteRole(String clientId, RoleDto role) {
    lock.writeLock().lock();
    try {
      List<RoleDto> roles = CLIENT_ROLE_MAP.getOrDefault(clientId, Collections.emptyList());
      List<RoleDto> roleDtos = roles.stream().filter(item -> !item.getId().equals(role.getId())).collect(Collectors.toList());
      CLIENT_ROLE_MAP.put(clientId, roleDtos);
      Map<String, List<PermissionDto>> rolePermissionMap = CLIENT_ROLES_PERMISSIONS_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
      rolePermissionMap.remove(role.getName());
      // add new updated role-permission to cache
      CLIENT_ROLES_PERMISSIONS_MAP.put(clientId, rolePermissionMap);
      Map<String, List<ResourceDto>> roleResourceMap = CLIENT_ROLES_RESOURCE_MAP.getOrDefault(clientId, Maps.newConcurrentMap());
      roleResourceMap.remove(role.getName());
      CLIENT_ROLES_RESOURCE_MAP.put(clientId, roleResourceMap);
    } finally {
      lock.writeLock().unlock();
    }
  }

}
