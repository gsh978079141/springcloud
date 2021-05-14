package com.gsh.springcloud.account.web;


import com.gsh.springcloud.account.client.ResourceClientV2;
import com.gsh.springcloud.account.dto.ResourceDetailDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.account.service.ResourceService;
import com.gsh.springcloud.common.vo.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v2/resources")
public class ResourceControllerV2 implements ResourceClientV2 {

  @Resource
  ResourceService resourceService;

  @Override
  public JsonResult<List<ResourceDetailDto>> listResources(String clientId) {
    return JsonResult.success(resourceService.listResourcesV2(clientId));
  }

  @Override
  public JsonResult<List<ResourceDetailDto>> listResourcesOfUser(String userId, String clientId) {
    return JsonResult.success(resourceService.listResourcesByUserV2(clientId, userId));
  }

  @Override
  public JsonResult<Map<String, List<ResourceDto>>> getRoleResourcesMap(String clientId, String[] roleNames) {
    return JsonResult.success(resourceService.getResourcesOfRoleMapV2(clientId, roleNames));
  }


}
