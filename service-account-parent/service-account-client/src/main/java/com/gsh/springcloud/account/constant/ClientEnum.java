package com.gsh.springcloud.account.constant;

/**
 * @author gsh
 */

public enum ClientEnum {

  EDU_MANAGEMENT_BFF(ClientConstants.ID_MANAGEMENT, "管理平台BFF");

  private String clientId;

  private String name;

  ClientEnum(String clientId, String name) {
    this.clientId = clientId;
    this.name = name;
  }


}
