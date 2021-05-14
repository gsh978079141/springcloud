package com.gsh.springcloud.account.client;

import com.gsh.springcloud.account.dto.*;
import com.gsh.springcloud.common.validation.UpdateValidate;
import com.gsh.springcloud.common.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @author wal
 * @date 2021/4/15 15:48
 */
@Api(tags = "Role API", value = "role")
@FeignClient(url = "${feign.url.service-account}", path = "/v2/roles", name = "roleClientV2")
public interface RoleClientV2 {
  @ApiOperation("获取客户端下所有角色列表")
  @GetMapping("/{client-id}")
  JsonResult<List<RoleDto>> listByClientId(@PathVariable("client-id") @NotNull(message = "clientId不能为空") String clientId);

  @ApiOperation("获取客户端下指定id的角色信息")
  @GetMapping("/{client-id}/{id}")
  JsonResult<RolePermissionsDto> getRolePermissionsById(@PathVariable("client-id") String clientId, @PathVariable("id") String id);

  @ApiOperation("")
  @GetMapping("exist/{client-id}")
  JsonResult<RoleExistDto> existInClient(@PathVariable("client-id") @NotNull String clientId,
                                         @RequestParam("name") String name);

  @ApiOperation("创建客户端角色")
  @PostMapping("")
  JsonResult<?> create(@RequestBody @Validated RoleUpdateDto dto);

  @ApiOperation("更新客户端指定角色")
  @PutMapping("")
  JsonResult<?> update(@RequestBody @Validated({UpdateValidate.class, Default.class}) RoleUpdateDto dto);

  @ApiOperation("删除客户端下指定角色")
  @DeleteMapping("/{client-id}/{id}")
  JsonResult<?> delete(@PathVariable("client-id") String clientId, @PathVariable("id") String id);

  @ApiOperation("检查客户端下指定角色是否在用")
  @GetMapping("/inUse/{client-id}/{id}")
  JsonResult<RoleInUseDto> checkInUse(@PathVariable("client-id") String clientId, @PathVariable("id") String id);
}
