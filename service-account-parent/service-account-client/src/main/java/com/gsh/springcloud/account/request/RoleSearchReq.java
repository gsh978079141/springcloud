package com.gsh.springcloud.account.request;

import lombok.Data;

@Data
public class RoleSearchReq extends BaseSearchPageReq {

  private String nameLike;
}
