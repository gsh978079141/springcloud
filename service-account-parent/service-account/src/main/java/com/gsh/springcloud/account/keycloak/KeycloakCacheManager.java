package com.gsh.springcloud.account.keycloak;

import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;

import java.util.Map;


/**
 * keycloak 缓存管理接口
 *
 * @author jun
 */
public interface KeycloakCacheManager {

  /**
   * 全量刷新client的权限认证数据
   * 不包括用户相关数据
   *
   * @param clientAuthorizationDataMap key为clientId, value为该client的权限认证数据
   */
  void refresh(Map<String, ClientAuthorizationDataDto> clientAuthorizationDataMap);

}
