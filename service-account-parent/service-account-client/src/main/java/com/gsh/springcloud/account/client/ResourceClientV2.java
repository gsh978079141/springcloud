package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.dto.ResourceDetailDto;
import com.gsh.springcloud.account.dto.ResourceDto;
import com.gsh.springcloud.common.vo.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(url = "${feign.url.service-account}/v2/resources", name = "resourceClientV2")
@Api(tags = "Resources API", value = "resource")
public interface ResourceClientV2 {

  @GetMapping("all/clients/{clientId}")
  JsonResult<List<ResourceDetailDto>> listResources(@PathVariable("clientId") String clientId);


  @GetMapping("users/{userId}/clients/{clientId}")
  JsonResult<List<ResourceDetailDto>> listResourcesOfUser(@PathVariable("userId") String userId, @PathVariable("clientId") String clientId);


  @GetMapping("roles/clients/{clientId}")
  JsonResult<Map<String, List<ResourceDto>>> getRoleResourcesMap(@PathVariable("clientId") String clientId, @RequestParam("roleName") String[] roleNames);

}
