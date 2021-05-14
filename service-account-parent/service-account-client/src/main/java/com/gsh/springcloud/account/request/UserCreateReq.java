package com.gsh.springcloud.account.request;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserCreateReq {

  @NotBlank
  @ApiModelProperty("used to login operation, perfect value is users' mobile number")
  private String loginUsername;

  private String firstName;

  private String lastName;

  @ApiModelProperty("init login password, if you don't set this field 4 user, this value will be set internal as loginUsername field")
  private String initPassword;

  @ApiModelProperty("email address, can be used to login")
  private String email;

  @Builder.Default
  @ApiModelProperty("the key is `client_id`, and the values are the role names that you wanna assign 2 this user on corresponded client")
  private Map<String, List<String>> initClientRoles = Maps.newHashMap();

  @Builder.Default
  @ApiModelProperty("values are the role names on the specific realm")
  private List<String> initRealmRoles = Lists.newArrayList();

  @Builder.Default
  @ApiModelProperty("key is attribute name and values are corresponded the attribute name")
  private Map<String, List<String>> attributesMap = Maps.newHashMap();

  @Tolerate
  public UserCreateReq() {
  }
}
