package com.gsh.springcloud.account.keycloak.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.gsh.springcloud.account.converter.ResourceConverter;
import com.gsh.springcloud.account.dto.*;
import com.gsh.springcloud.account.keycloak.KeycloakCache;
import com.gsh.springcloud.account.keycloak.KeycloakCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangal
 */
@Component
public class KeycloakRedisCacheImpl implements KeycloakCacheManager, KeycloakCache {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Resource
  private ResourceConverter resourceConverter;

  @Value("${spring.application.name}.${spring.profiles.active}.keycloak.")
  private String keycloakKeyPrefix;

  @Override
  public void refresh(Map<String, ClientAuthorizationDataDto> clientAuthorizationDataMap) {
    clientAuthorizationDataMap.forEach((clientId, data) -> {
      String rolesKey = getRolesKey(clientId);
      String rolePermissionsKey = getRolePermissionsKey(clientId);
      String roleResourcesKey = getRoleResourcesKey(clientId);
      redisTemplate.opsForValue().set(getResourcesKey(clientId), data.getResources());
      redisTemplate.opsForValue().set(getPermissionsKey(clientId), data.getPermissions());
      redisTemplate.opsForHash().putAll(rolesKey,
              data.getRoles().stream().collect(Collectors.toMap(RoleDto::getName, roleDto -> roleDto)));
      redisTemplate.opsForValue().set(getResourcePermissionsKey(clientId), data.getResourcePermissionsMap());
      redisTemplate.opsForHash().putAll(rolePermissionsKey, data.getRolePermissionsMap());
      putRoleResources(roleResourcesKey, data.getResources(),
              data.getRolePermissionsMap(), data.getResourcePermissionsMap());
      // 之所以不先直接清空该hash的所有值然后全量更新，是担心清空操作和全量更新操作之间会有查询请求进来，导致取不到数据
      // 所以先覆盖存在的所有role的数据，再检查出无效的role并删除
      Set<String> newRoles = data.getRolePermissionsMap().keySet();
      Set<String> curRoles = redisTemplate.<String, RoleDto>boundHashOps(getRolesKey(clientId)).keys();
      if (Objects.nonNull(curRoles)) {
        curRoles.removeAll(newRoles);
        if (!curRoles.isEmpty()) {
          redisTemplate.opsForHash().delete(rolesKey, curRoles.toArray());
          redisTemplate.opsForHash().delete(rolePermissionsKey, curRoles.toArray());
          redisTemplate.opsForHash().delete(roleResourcesKey, curRoles.toArray());
        }
      }
    });
  }

  private void putRoleResources(String roleResourcesKey, List<ResourceDto> resourceList, Map<String, List<PermissionDto>> rolePermissionsMap,
                                Map<String, List<PermissionDto>> resourcePermissionsMap) {
    Map<String, ResourceDto> resourceDtoMap = resourceList.stream()
            .collect(Collectors.toMap(ResourceDto::getName, resourceDto -> resourceDto));
    Map<String, List<ResourceDto>> roleResourceMap = Maps.newHashMap();
    rolePermissionsMap.forEach((k, v) -> {
      List<ResourceDto> resources = roleResourceMap.getOrDefault(k, Lists.newArrayList());
      v.forEach(permissionDto -> {
        Set<String> existResource = resources.stream().map(ResourceDto::getName).collect(Collectors.toSet());
        resourcePermissionsMap.forEach((key, value) -> value.forEach(pDto -> {
          if (permissionDto.getName().equals(pDto.getName())) {
            if (!existResource.contains(key)) {
              resources.add(resourceDtoMap.get(key));
            }
          }
        }));
        roleResourceMap.put(k, resources);
      });
      redisTemplate.opsForHash().put(roleResourcesKey, k, resources);
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ResourceDto> listAllResources(String clientId) {
    Object value = redisTemplate.opsForValue().get(getResourcesKey(clientId));
    return value != null ? (List<ResourceDto>) value : Lists.newArrayList();

  }

  @Override
  @SuppressWarnings("unchecked")
  public List<PermissionDto> listAllPermissions(String clientId) {
    return (List<PermissionDto>) redisTemplate.opsForValue().get(getPermissionsKey(clientId));
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, List<PermissionDto>> listAllResourcePermissions(String clientId) {
    return (Map<String, List<PermissionDto>>) redisTemplate.opsForValue().get(getResourcePermissionsKey(clientId));
  }

  @Override
  public Map<String, List<ResourceDto>> getResourcesOfRoleMap(String clientId, Set<String> roleNames) {
    Map<String, List<ResourceDto>> roleResourcesMap = Maps.newHashMap();
    roleNames.forEach(roleName -> {
      List<ResourceDto> resources = redisTemplate.<String, List<ResourceDto>>boundHashOps(getRoleResourcesKey(clientId)).get(roleName);
      roleResourcesMap.put(roleName, resources == null ? Lists.newArrayList() : resources);
    });
    return roleResourcesMap;
  }

  @Override
  public List<ResourceDetailDto> listResourceDetailsByRoleNames(String clientId, Set<String> roleNames) {
    List<ResourceDetailDto> resourceDetails = Lists.newArrayList();
    roleNames.forEach(roleName -> {
      List<ResourceDto> roleResources = redisTemplate.<String, List<ResourceDto>>boundHashOps(getRoleResourcesKey(clientId))
              .get(roleName);
      roleResources = roleResources == null ? Lists.newArrayList() : roleResources;
      List<PermissionDto> rolePermissions = redisTemplate.<String, List<PermissionDto>>boundHashOps(getRolePermissionsKey(clientId))
              .get(roleName);
      roleResources.forEach(resourceDto -> {
        Map<String, List<PermissionDto>> resourcePermissionsMap = listAllResourcePermissions(clientId);
        List<PermissionDto> resourcePermissions = resourcePermissionsMap.getOrDefault(resourceDto.getName(),
                Lists.newArrayList());
        if (Objects.nonNull(rolePermissions)) {
          resourcePermissions.retainAll(rolePermissions);
        }
        ResourceDetailDto resourceDetailDto = resourceConverter.dto2detailDto(resourceDto);
        resourceDetailDto.setAssociatedPermissions(resourcePermissions);
        resourceDetails.add(resourceDetailDto);
      });
    });
    return resourceDetails;
  }

  @Override
  public boolean containsRoles(String clientId, Set<String> roleNames) {
    Set<String> keys = redisTemplate.<String, Set<String>>boundHashOps(getRolesKey(clientId)).keys();
    keys = keys == null ? Sets.newHashSet() : keys;
    return keys.stream().anyMatch(roleNames::contains);
  }

  @Override
  public List<PermissionDto> listPermissionsOfRoles(String clientId, Set<String> roleNames) {
    List<PermissionDto> permissions = Lists.newArrayList();
    roleNames.forEach(roleName -> {
      List<PermissionDto> permissionDtoList = redisTemplate.<String, List<PermissionDto>>boundHashOps(getRolePermissionsKey(clientId))
              .get(roleName);
      permissionDtoList = permissionDtoList == null ? Lists.newArrayList() : permissionDtoList;
      permissions.addAll(permissionDtoList);
    });
    return permissions;
  }

  @Override
  public boolean hasPermissionInRoles(String clientId, Set<String> roleNames, String permission) {
    for (String roleName : roleNames) {
      List<PermissionDto> permissions = redisTemplate.<String, List<PermissionDto>>boundHashOps(getRolePermissionsKey(clientId))
              .get(roleName);
      if (Objects.nonNull(permissions)) {
        for (PermissionDto permissionDto : permissions) {
          if (permissionDto.getName().equals(permission)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public List<RoleDto> listAllRoles(String clientId) {
    return redisTemplate.<String, RoleDto>opsForHash().values(getRolesKey(clientId));
  }

  @Override
  public List<RoleDto> listRolesByNames(String clientId, Set<String> roleNames) {
    List<RoleDto> roles = Lists.newArrayList();
    roleNames.forEach(roleName -> {
      RoleDto roleDto = (RoleDto) redisTemplate.boundHashOps(getRolesKey(clientId)).get(roleName);
      if (roleDto != null) {
        roles.add(roleDto);
      }
    });
    return roles;
  }

  @Override
  public Map<String, List<ResourceDto>> listAllRoleResources(String clientId) {
    return redisTemplate.<String, List<ResourceDto>>boundHashOps(getRoleResourcesKey(clientId)).entries();
  }

  @Override
  public Map<String, List<PermissionDto>> listAllRolesPermissions(String clientId) {
    return redisTemplate.<String, List<PermissionDto>>boundHashOps(getRolePermissionsKey(clientId)).entries();
  }

  @Override
  public void updateRole(String clientId, RoleDto role) {
    redisTemplate.boundHashOps(getRolesKey(clientId)).put(role.getName(), role);
  }

  @Override
  public void updateRolePermissions(String clientId, String roleName, Set<String> permissions) {
    Map<String, PermissionDto> permissionDtoMap = listAllPermissions(clientId).stream()
            .collect(Collectors.toMap(PermissionDto::getName, permissionDto -> permissionDto));
    List<PermissionDto> permissionDtoList = Lists.newArrayList();
    for (String pName : permissions) {
      PermissionDto permissionDto = permissionDtoMap.get(pName);
      if (Objects.nonNull(permissionDto)) {
        permissionDtoList.add(permissionDto);
      }
    }
    Map<String, List<PermissionDto>> rolePermissionsMap = Maps.newHashMap();
    rolePermissionsMap.put(roleName, permissionDtoList);
    redisTemplate.boundHashOps(getRolePermissionsKey(clientId)).put(roleName, permissionDtoList);

    putRoleResources(getRoleResourcesKey(clientId), listAllResources(clientId), rolePermissionsMap, listAllResourcePermissions(clientId));
  }

  @Override
  public void deleteRole(String clientId, RoleDto role) {
    if (role != null) {
      redisTemplate.boundHashOps(getRolesKey(clientId)).delete(role.getName());
      redisTemplate.boundHashOps(getRolePermissionsKey(clientId)).delete(role.getName());
      redisTemplate.boundHashOps(getRoleResourcesKey(clientId)).delete(role.getName());
    }
  }

  private String getResourcesKey(String clientId) {
    return keycloakKeyPrefix + clientId + ".resources";
  }

  private String getPermissionsKey(String clientId) {
    return keycloakKeyPrefix + clientId + ".permissions";
  }

  private String getRolesKey(String clientId) {
    return keycloakKeyPrefix + clientId + ".roles";
  }

  private String getResourcePermissionsKey(String clientId) {
    return keycloakKeyPrefix + clientId + ".resourcePermissions";
  }

  private String getRolePermissionsKey(String clientId) {
    return keycloakKeyPrefix + clientId + ".rolePermissions";
  }

  private String getRoleResourcesKey(String clientId) {
    return keycloakKeyPrefix + clientId + ".roleResources";
  }
}
