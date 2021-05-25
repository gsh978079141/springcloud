package com.gsh.springcloud.order.domain.entity;

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
 * (Order)表实体类
 *
 * @author EasyCode
 */
@Data
@SuperBuilder
@TableName("order")
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

  private static final long serialVersionUID = -1;

  /**
   * id
   */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 订单名称
   */
  private String name;

  /**
   * 订单号
   */
  private String orderNo;

  /**
   * 价格
   */
  private Double price;

  /**
   * 购买数量
   */
  private Integer num;

  /**
   * 用户id
   */
  private Long userId;

  /**
   * 下单时间
   */
  private Date orderTime;

}