package com.gsh.springcloud.account.request;


import com.gsh.springcloud.account.support.ObjectRequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSearchReq extends BaseSearchPageReq {

  private String username;

  @ObjectRequestParameter("first_name")
  @ApiModelProperty(name = "first_name")
  private String firstName;

  @ObjectRequestParameter("last_name")
  @ApiModelProperty(name = "last_name")
  private String lastName;

  private String email;

}
