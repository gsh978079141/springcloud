package com.gsh.springcloud.account.web;

import com.gsh.springcloud.account.client.RoleClientV2;
import com.gsh.springcloud.account.dto.*;
import com.gsh.springcloud.account.exception.RoleNameLockedException;
import com.gsh.springcloud.account.service.RoleService;
import com.gsh.springcloud.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wal
 * @date 2021/4/15 16:01
 */
@RestController
@RequestMapping("/v2/roles")
@Slf4j
public class RoleControllerV2 implements RoleClientV2 {

  @Resource
  RoleService roleService;

  @Resource
  RedissonClient redissonClient;

  @Override
  public JsonResult<List<RoleDto>> listByClientId(@NotNull(message = "clientId不能为空") String clientId) {
    return JsonResult.success(roleService.listByClientId(clientId));
  }

  @Override
  public JsonResult<RolePermissionsDto> getRolePermissionsById(String clientId, String id) {
    return JsonResult.success(roleService.getRolePermissionsById(clientId, id));
  }

  @Override
  public JsonResult<RoleExistDto> existInClient(@NotNull String clientId, String name) {
    return JsonResult.success(roleService.existInClient(clientId, name));
  }

  @Override
  public JsonResult<?> create(RoleUpdateDto dto) {
    RLock lock = redissonClient.getLock(dto.getName());
    boolean locked = lock.tryLock();
    if (!locked) {
      throw new RoleNameLockedException(dto.getName());
    }
    log.info("{} locked", dto.getName());
    dto.setClientRole(true);
    try {
      roleService.create(dto);
    } catch (Exception e) {
      //捕获创建异常，防止因异常导致锁未释放
      log.error(e.getMessage());
      return JsonResult.failureWithMessage(e.getMessage());
    } finally {
      lock.unlock();
      log.info("{} unlocked", dto.getName());
    }
    return JsonResult.success();
  }

  @Override
  public JsonResult<?> update(RoleUpdateDto dto) {
    roleService.update(dto);
    return JsonResult.success();
  }

  @Override
  public JsonResult<?> delete(String clientId, String id) {
    roleService.delete(clientId, id);
    return JsonResult.success();
  }

  @Override
  public JsonResult<RoleInUseDto> checkInUse(String clientId, String id) {
    return JsonResult.success(roleService.checkUsing(clientId, id));
  }
}
