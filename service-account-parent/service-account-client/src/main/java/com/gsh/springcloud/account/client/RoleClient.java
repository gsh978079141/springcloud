package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.request.RoleCreateReq;
import com.gsh.springcloud.account.request.RoleUpdateReq;
import com.gsh.springcloud.account.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author jasonlee
 */
@FeignClient(url = "${feign.url.service-account}/roles", name = "roleClient")
@Api(tags = "Role API", value = "role")
public interface RoleClient {

  @ApiOperation(value = "create a role on specific client", nickname = "create4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @PostMapping("clients/{client-id}")
  void create4client(@PathVariable("client-id") String clientId, @RequestBody RoleCreateReq req);

  @ApiOperation(value = "list all roles on specific client", nickname = "listAll4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("all/clients/{client-id}")
  RoleListResp listAll4client(@PathVariable("client-id") String clientId);


  @ApiOperation(value = "list all roles on specific client", nickname = "listAll4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("existences/clients/{client-id}")
  ExistenceResp existenceInClient(@PathVariable("client-id") String clientId, @RequestParam("name") String name);

  @ApiOperation(value = "get detail of role on specific client", nickname = "detail4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("{id}/detail/clients/{client-id}")
  RoleDetailResp detail4client(@PathVariable("client-id") String clientId, @PathVariable("id") String id);


  @ApiOperation(value = "get detail of role on specific client by name", nickname = "detail4clientByName", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("name-{name}/detail/clients/{client-id}")
  RoleDetailResp detail4clientByName(@PathVariable("client-id") String clientId, @PathVariable("name") String name);

  @ApiOperation(value = "get detail of role on specific client", nickname = "detail4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("{id}/clients/{client-id}")
  RoleResp get4clientById(@PathVariable("client-id") String clientId, @PathVariable("id") String id);


  @ApiOperation(value = "get detail of role on specific client by name", nickname = "get4clientByName", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("name-{name}/clients/{client-id}")
  RoleResp get4clientByName(@PathVariable("client-id") String clientId, @PathVariable("name") String name);

  @ApiOperation(value = "delete role by name on specific client", nickname = "delete4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @DeleteMapping("{id}/clients/{client-id}")
  void delete4client(@PathVariable("client-id") String clientId, @PathVariable("id") String id);

  @ApiOperation(value = "update role by name on specific client", nickname = "update4client", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @PutMapping("{id}/clients/{client-id}")
  void update4client(@PathVariable("client-id") String clientId, @PathVariable("id") String id, @RequestBody RoleUpdateReq req);

  @ApiOperation(value = "check if the role is in use", nickname = "checkInUse", tags = {"roles",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping("{id}/in-use-verifications/clients/{client-id}")
  RoleInUseResp checkInUse(@PathVariable("client-id") String clientId, @PathVariable("id") String id);
}
