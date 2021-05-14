package com.gsh.springcloud.account.service;

import com.gsh.springcloud.account.constant.AuthDataChangeActionEnum;
import com.gsh.springcloud.account.dto.RoleChangeDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthChangePubService {

  /**
   * 发布角色数据变化事件
   *
   * @param action
   * @param roleChange
   */
  void pubRoleDataChange(String clientId, AuthDataChangeActionEnum action, RoleChangeDto roleChange);


}
