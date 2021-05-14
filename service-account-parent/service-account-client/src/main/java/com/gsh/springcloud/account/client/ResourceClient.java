package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.response.ResourceListResp;
import com.gsh.springcloud.account.response.RoleResourceMapResp;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${feign.url.service-account}/resources", name = "resourceClient")
@Api(tags = "Resources API", value = "resource")
public interface ResourceClient {

  @GetMapping("all/clients/{client-id}")
  ResourceListResp listResources(@PathVariable("client-id") String clientId);


  @GetMapping("users/{user-id}/clients/{client-id}")
  ResourceListResp listResourcesOfUser(@PathVariable("user-id") String userId, @PathVariable("client-id") String clientId);


  @GetMapping("roles/clients/{client-id}")
  RoleResourceMapResp getRoleResourcesMap(@PathVariable("client-id") String clientId, @RequestParam("roleName") String[] roleNames);

}
