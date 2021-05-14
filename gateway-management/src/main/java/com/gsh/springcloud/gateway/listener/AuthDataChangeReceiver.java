package com.gsh.springcloud.gateway.listener;

import com.alibaba.fastjson.JSONObject;
import com.gsh.springcloud.gateway.constant.ActionEnum;
import com.gsh.springcloud.gateway.constant.AuthDataTypeConstants;
import com.gsh.springcloud.gateway.domain.AuthDataChange;
import com.gsh.springcloud.gateway.domain.RoleAndPermission;
import com.gsh.springcloud.gateway.keycloak.AuthDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author maj
 */
@Component
@Slf4j
public class AuthDataChangeReceiver {

  @Resource
  private AuthDataCache authDataCache;


  @RabbitListener(queues = "${rabbit-mq.auth-data-queue}")
  public void receiveAuthDataChange(AuthDataChange authDataChange) {
    log.info("come in auth-data-queue receive");
    JSONObject payLoad = authDataChange.getPayLoad();
    String dataType = authDataChange.getDataType();
    String type = authDataChange.getType();
    if (AuthDataTypeConstants.ROLE_AND_PERMISSION.equalsIgnoreCase(dataType)) {
      RoleAndPermission roleAndPermission = JSONObject.toJavaObject(payLoad, RoleAndPermission.class);
      String roleName = roleAndPermission.getRoleName();
      switch (ActionEnum.valueOf(type)) {
        case ADD_ROLE:
        case UPDATE_ROLE:
          Set<String> permissions = roleAndPermission.getPermissions();
          authDataCache.saveRole(roleName, permissions);
          break;
        case DELETE_ROLE:
          authDataCache.deleteRole(roleName);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + ActionEnum.valueOf(type));
      }
    }
  }

}
