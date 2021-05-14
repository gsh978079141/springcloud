package com.gsh.springcloud.account.service;

import com.gsh.springcloud.account.dto.ResourceDetailDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.response.ResourceListResp;
import com.gsh.springcloud.account.response.RoleResourceMapResp;

import java.util.List;
import java.util.Map;

public interface ResourceService {

  ResourceListResp listResources(String clientId);

  List<ResourceDetailDto> listResourcesV2(String clientId);

  ResourceListResp listResourcesByUser(String clientId, String userId);

  List<ResourceDetailDto> listResourcesByUserV2(String clientId, String userId);

  RoleResourceMapResp getResourcesOfRoleMap(String clientId, String[] roleNames);

  Map<String, List<ResourceDto>> getResourcesOfRoleMapV2(String clientId, String[] roleNames);
}
