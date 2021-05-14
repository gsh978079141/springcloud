package com.gsh.springcloud.account.service.impl;

import com.google.common.collect.Lists;
import com.gsh.springcloud.account.converter.ResourceConverter;
import com.gsh.springcloud.account.dto.PermissionDto;
import com.gsh.springcloud.account.dto.ResourceDetailDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.dto.RoleDto;
import com.gsh.springcloud.account.keycloak.KeycloakCache;
import com.gsh.springcloud.account.keycloak.KeycloakHandlerService;
import com.gsh.springcloud.account.response.ResourceListResp;
import com.gsh.springcloud.account.response.RoleResourceMapResp;
import com.gsh.springcloud.account.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

  @Resource
  private KeycloakCache keycloakCache;

  @Resource
  private KeycloakHandlerService keycloakHandlerService;

  @Resource
  private ResourceConverter resourceConverter;

  @Override
  public ResourceListResp listResources(String clientId) {
    // resource全量数据都在缓存中，直接从缓存中获取
    List<ResourceDto> resources = keycloakCache.listAllResources(clientId);
    Map<String, List<PermissionDto>> resourcePermissionsMap = keycloakCache.listAllResourcePermissions(clientId);

    List<ResourceDetailDto> resourceDetails = Lists.newArrayList();
    //合并 resources resourcePermissionsMap 得到 resourceDetails
    resources.forEach(resourceDto -> {
      List<PermissionDto> permissionDto = resourcePermissionsMap.getOrDefault(resourceDto.getName(), Lists.newArrayList());
      ResourceDetailDto resourceDetailDto = resourceConverter.dto2detailDto(resourceDto);
      resourceDetailDto.setAssociatedPermissions(permissionDto);
      resourceDetails.add(resourceDetailDto);
    });
    return new ResourceListResp(resourceDetails);
  }

  @Override
  public List<ResourceDetailDto> listResourcesV2(String clientId) {
    // resource全量数据都在缓存中，直接从缓存中获取
    List<ResourceDto> resources = keycloakCache.listAllResources(clientId);
    Map<String, List<PermissionDto>> resourcePermissionsMap = keycloakCache.listAllResourcePermissions(clientId);

    List<ResourceDetailDto> resourceDetails = Lists.newArrayList();
    //合并 resources resourcePermissionsMap 得到 resourceDetails
    resources.forEach(resourceDto -> {
      List<PermissionDto> permissionDto = resourcePermissionsMap.getOrDefault(resourceDto.getName(), Lists.newArrayList());
      ResourceDetailDto resourceDetailDto = resourceConverter.dto2detailDto(resourceDto);
      resourceDetailDto.setAssociatedPermissions(permissionDto);
      resourceDetails.add(resourceDetailDto);
    });
    return resourceDetails;
  }

  @Override
  public ResourceListResp listResourcesByUser(String clientId, String userId) {
    // 用户绑定的角色列表，需要从keycloak中查询
    List<RoleDto> roles = keycloakHandlerService.listRolesOfUser(clientId, userId);
    Set<String> roleNames = roles.stream().map(RoleDto::getName).collect(Collectors.toSet());
    List<ResourceDetailDto> resourceDetails = keycloakCache.listResourceDetailsByRoleNames(clientId, roleNames);
    return new ResourceListResp(resourceDetails);
  }

  @Override
  public List<ResourceDetailDto> listResourcesByUserV2(String clientId, String userId) {
    // 用户绑定的角色列表，需要从keycloak中查询
    List<RoleDto> roles = keycloakHandlerService.listRolesOfUser(clientId, userId);
    Set<String> roleNames = roles.stream().map(RoleDto::getName).collect(Collectors.toSet());
    List<ResourceDetailDto> resourceDetails = keycloakCache.listResourceDetailsByRoleNames(clientId, roleNames);
    return resourceDetails;
  }

  @Override
  public RoleResourceMapResp getResourcesOfRoleMap(String clientId, String[] roleNames) {
    RoleResourceMapResp roleResourceMapResp = new RoleResourceMapResp();
    roleResourceMapResp.setMap(keycloakCache.getResourcesOfRoleMap(clientId, Arrays.stream(roleNames).collect(Collectors.toSet())));
    return roleResourceMapResp;
  }

  @Override
  public Map<String, List<ResourceDto>> getResourcesOfRoleMapV2(String clientId, String[] roleNames) {
    return keycloakCache.getResourcesOfRoleMap(clientId, Arrays.stream(roleNames).collect(Collectors.toSet()));
  }
}
