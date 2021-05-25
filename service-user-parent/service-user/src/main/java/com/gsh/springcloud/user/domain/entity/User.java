package com.gsh.springcloud.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(User)表实体类
 *
 * @author EasyCode
 */
@Data
@SuperBuilder
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = -1;

  /**
   * ID
   */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 密码
   */
  private String password;

  /**
   * 年龄
   */
  private Integer age;

  /**
   * 积分
   */
  private Double integral;

  /**
   * 创建时间
   */
  private Date createdTime;

  /**
   * 更新时间
   */
  private Date updatedTime;

  /**
   * 创建者
   */
  private String createdBy;

  /**
   * 更新者
   */
  private String updatedBy;

  /**
   * 是否删除
   */
  private Boolean deleted;

}