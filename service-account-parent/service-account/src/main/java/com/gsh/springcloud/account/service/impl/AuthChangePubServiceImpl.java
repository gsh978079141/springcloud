package com.gsh.springcloud.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gsh.springcloud.account.config.RabbitMqProperties;
import com.gsh.springcloud.account.constant.AuthDataChangeActionEnum;
import com.gsh.springcloud.account.constant.AuthDataTypeConstants;
import com.gsh.springcloud.account.dto.AuthDataChange;
import com.gsh.springcloud.account.dto.RoleChangeDto;
import com.gsh.springcloud.account.service.AuthChangePubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jun
 */
@Slf4j
@Service
public class AuthChangePubServiceImpl implements AuthChangePubService {

  @Resource
  private RabbitTemplate rabbitTemplate;

  @Resource
  private RabbitMqProperties rabbitMqProperties;


  @Override
  public void pubRoleDataChange(String clientId, AuthDataChangeActionEnum action, RoleChangeDto roleChange) {
    AuthDataChange authDataChange = AuthDataChange.builder()
            .type(action.toString())
            .dataType(AuthDataTypeConstants.ROLE_AND_PERMISSION)
            .payLoad((JSONObject) JSONObject.toJSON(roleChange)).build();
    String routingKey = rabbitMqProperties.getAuthDataRoutingPrefix() + clientId;
    rabbitTemplate.convertAndSend(rabbitMqProperties.getAuthDataExchange(), routingKey, authDataChange);
    log.info("pubRoleDataChange success::::{} ", JSON.toJSONString(authDataChange));
  }

}
