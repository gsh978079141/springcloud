package com.gsh.springcloud.account.service;

import com.gsh.springcloud.account.dto.ClientAuthorizationDataDto;

import java.util.Set;

/**
 * 附加服务
 *
 * @author gsh
 */
public interface ExtraService {

  /**
   * 导出指定client的权限数据
   *
   * @param realmName
   * @param clientId
   * @return
   */
  ClientAuthorizationDataDto exportAuthorizationData(String realmName, String clientId, Set<String> targetRoles);

  /**
   * 导出client的权限数据
   *
   * @param data
   */
  void importAuthorizationData(ClientAuthorizationDataDto data);

}
