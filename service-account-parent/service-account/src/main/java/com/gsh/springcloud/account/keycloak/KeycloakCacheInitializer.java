package com.gsh.springcloud.account.keycloak;


import com.google.common.collect.Maps;
import com.gsh.springcloud.account.constant.ClientConstants;
import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


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
  private KeycloakCacheManager keycloakCacheManager;


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
      Map<String, ClientAuthorizationDataDto> clientDataMap = Maps.newHashMap();
      ClientConstants.CACHED_CLIENTS.forEach(clientId -> {
        ClientAuthorizationDataDto clientData = keycloakHandlerService.getClientDataOfCurRealm(clientId);
        clientDataMap.put(clientId, clientData);
      });
      keycloakCacheManager.refresh(clientDataMap);
    } catch (Exception exception) {
      log.error("initial keycloakCache error", exception);
    }

    log.info("Initial keycloakCache finished, cost {} ms", System.currentTimeMillis() - start);
  }
}
