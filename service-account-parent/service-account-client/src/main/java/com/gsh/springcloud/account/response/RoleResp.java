package com.gsh.springcloud.account.response;

import com.google.common.collect.Maps;
import com.gsh.springcloud.common.BaseResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RoleResp extends BaseResponse {

  private String id;

  private String name;

  private Boolean clientRole;

  private String clientId;

  private String description;

  private Map<String, List<String>> attributes = Maps.newHashMap();

  public boolean isEditable() {
    // TODO attributes 通过api暂时无法获取到内容，需要排查原因后根据attributes属性来判断
    return !"系统管理员".equals(this.name) && !"学校管理员".equals(this.name);
  }

}
