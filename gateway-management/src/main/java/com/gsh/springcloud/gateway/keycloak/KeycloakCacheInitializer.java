package com.gsh.springcloud.gateway.keycloak;


import com.gsh.springcloud.gateway.domain.ClientAuthorizationDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * keycloak cache 数据初始化加载器
 *
 * @author gsh
 */
@Component
@Slf4j
public class KeycloakCacheInitializer implements ApplicationRunner {

  @Resource
  private KeycloakHandlerService keycloakHandlerService;

  @Resource
  private AuthDataCache authDataCache;

  /**
   * 加载当前realm下应用需要缓存的client的全线数据
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(ApplicationArguments args) {
    long start = System.currentTimeMillis();
    try {
      ClientAuthorizationDataDto clientData = keycloakHandlerService.getClientData();
      authDataCache.refresh(clientData);
    } catch (Exception exception) {
      log.error("Initial keycloakCache error", exception);
      throw new RuntimeException("Initial keycloakCache error");
    }
    log.info("Initial keycloakCache finished, cost {} ms", System.currentTimeMillis() - start);
  }
}
