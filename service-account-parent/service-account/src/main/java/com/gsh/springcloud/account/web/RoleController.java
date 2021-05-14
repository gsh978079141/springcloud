package com.gsh.springcloud.account.web;


import com.gsh.springcloud.account.client.RoleClient;
import com.gsh.springcloud.account.exception.RoleNameLockedException;
import com.gsh.springcloud.account.request.RoleCreateReq;
import com.gsh.springcloud.account.request.RoleUpdateReq;
import com.gsh.springcloud.account.response.*;
import com.gsh.springcloud.account.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController implements RoleClient {

  @Resource
  RoleService roleService;

  @Resource
  RedissonClient redissonClient;

  @Override
  public void create4client(@PathVariable("client-id") String clientId, @Valid @RequestBody RoleCreateReq req) {
    RLock lock = redissonClient.getLock(req.getName());
    boolean locked = lock.tryLock();
    if (!locked) {
      throw new RoleNameLockedException(req.getName());
    }
    log.info("{} locked", req.getName());
    req.setClientRole(true);
    try {
      roleService.create4client(clientId, req);
    } catch (Exception e) {
      //捕获创建异常，防止因异常导致锁未释放
      log.error(e.getMessage());
    } finally {
      lock.unlock();
      log.info("{} unlocked", req.getName());
    }
  }

  @Override
  public RoleDetailResp detail4client(@PathVariable("client-id") String clientId, @PathVariable String id) {
    return roleService.detail4client(clientId, id);
  }

  @Override
  public RoleDetailResp detail4clientByName(String clientId, String name) {
    return roleService.detail4clientByName(clientId, name);
  }

  @Override
  public RoleResp get4clientById(@PathVariable("client-id") String clientId, @PathVariable String id) {
    return roleService.getById(clientId, id);
  }

  @Override
  public RoleResp get4clientByName(@PathVariable("client-id") String clientId, @PathVariable String name) {
    return roleService.getByName(clientId, name);
  }

  @Override
  public RoleListResp listAll4client(@PathVariable("client-id") String clientId) {
    return roleService.listAll4client(clientId);
  }

  @Override
  public ExistenceResp existenceInClient(String clientId, String name) {
    return roleService.checkExistence(clientId, name);
  }

  @Override
  public void delete4client(@PathVariable("client-id") String clientId, @PathVariable String id) {
    roleService.delete4client(clientId, id);
  }

  @Override
  public void update4client(@PathVariable("client-id") String clientId, @PathVariable String id, @Valid @RequestBody RoleUpdateReq req) {
    roleService.update4client(clientId, id, req);
  }

  @Override
  public RoleInUseResp checkInUse(@PathVariable("client-id") String clientId, @PathVariable String id) {
    return roleService.checkInUse(clientId, id);
  }
}
