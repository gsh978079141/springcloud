package com.gsh.springcloud.account.response;

import lombok.Data;

/**
 * @program: EDU_DBP_APP
 * @description:
 * @author: Gsh
 * @create: 2020-09-13 14:56
 **/
@Data
public class ExportAuthorizationRespClient {

  private String name;

  private Boolean bearerOnly;

}
