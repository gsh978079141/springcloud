package com.gsh.springcloud.account.response;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gsh.springcloud.common.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserResp extends BaseResponse {

  private String username;

  private String id;

  private String firstName;

  private String lastName;

  private String email;

  @ApiModelProperty("the key is `client_id`, and the values are the role names that you wanna assign 2 this user on corresponded client")
  private Map<String, List<String>> clientRolesMap = Maps.newHashMap();

  @ApiModelProperty("values are the role names on the specific realm")
  private List<String> realmRoles = Lists.newArrayList();

  @ApiModelProperty("key is attribute name and values are corresponded the attribute name")
  private Map<String, List<String>> attributesMap = Maps.newHashMap();

}