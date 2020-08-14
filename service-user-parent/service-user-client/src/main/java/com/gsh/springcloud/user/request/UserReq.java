package com.gsh.springcloud.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author gsh
 * @since 2020-01-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {

  private Integer id;

  private String userName;

  private String password;

  private Integer age;

  private Integer integral;

  private Date updateTime;

}
