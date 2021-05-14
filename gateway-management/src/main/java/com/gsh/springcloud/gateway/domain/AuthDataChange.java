package com.gsh.springcloud.gateway.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author maj
 */
@Data
public class AuthDataChange {
  String type;
  String dataType;
  JSONObject payLoad;
}
