package com.gsh.springcloud.gateway.jobs;

import com.gsh.springcloud.gateway.domain.ClientAuthorizationDataDto;
import com.gsh.springcloud.gateway.keycloak.AuthDataCache;
import com.gsh.springcloud.gateway.keycloak.KeycloakHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gsh
 */
@Slf4j
@Component
public class RefreshCacheJob {

  @Resource
  private KeycloakHandlerService keycloakHandlerService;

  @Resource
  private AuthDataCache authDataCache;

  /**
   * 由于正常业务操作不会改变权限数据，此任务不会刷新权限数据，只会刷新权限和角色的绑定关系
   */

  @Scheduled(initialDelay = 60 * 60 * 1000, fixedDelay = 60 * 60 * 1000)
  public void refreshRolePermissions() {
    log.info("定时刷新角色权限缓存开始！");
    try {
      ClientAuthorizationDataDto clientData = keycloakHandlerService.getClientData();
      authDataCache.refresh(clientData);
    } catch (Exception exception) {
      log.error("Initial keycloakCache error", exception);
      throw new RuntimeException("Initial keycloakCache error");
    }
    log.info("定时刷新角色权限缓存完成！");
  }

}
