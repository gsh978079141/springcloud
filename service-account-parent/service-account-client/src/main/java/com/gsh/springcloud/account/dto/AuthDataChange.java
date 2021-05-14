package com.gsh.springcloud.account.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthDataChange implements Serializable {
  String type;
  String dataType;
  JSONObject payLoad;
}
