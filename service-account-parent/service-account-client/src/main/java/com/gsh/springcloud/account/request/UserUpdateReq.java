package com.gsh.springcloud.account.request;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserUpdateReq {

  private String userName;

  private String lastName;

  private String firstName;

  private String email;

  private String initPassword;

  @ApiModelProperty("the key is `client_id`, and the values are the role names that you wanna assign 2 this user on corresponded client")
  private Map<String, List<String>> clientRolesMap = Maps.newHashMap();

  @ApiModelProperty("values are the role names on the specific realm")
  private List<String> realmRoles = Lists.newArrayList();

  @Builder.Default
  @ApiModelProperty("key is attribute name and values are corresponded the attribute name")
  private Map<String, String> attributes = Maps.newHashMap();


  @Tolerate
  public UserUpdateReq() {
  }
}
