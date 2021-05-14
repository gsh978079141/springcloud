package com.gsh.springcloud.account.web;


import com.gsh.springcloud.account.client.ResourceClient;
import com.gsh.springcloud.account.response.ResourceListResp;
import com.gsh.springcloud.account.response.RoleResourceMapResp;
import com.gsh.springcloud.account.service.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/resources")
public class ResourceController implements ResourceClient {

  @Resource
  ResourceService resourceService;

  @Override
  @GetMapping("all/clients/{client-id}")
  public ResourceListResp listResources(@PathVariable("client-id") String clientId) {
    return resourceService.listResources(clientId);
  }

  @Override
  public ResourceListResp listResourcesOfUser(@PathVariable("user-id") String userId, @PathVariable("client-id") String clientId) {
    return resourceService.listResourcesByUser(clientId, userId);
  }

  @Override
  public RoleResourceMapResp getRoleResourcesMap(String clientId, String[] roleNames) {
    return resourceService.getResourcesOfRoleMap(clientId, roleNames);
  }


}
