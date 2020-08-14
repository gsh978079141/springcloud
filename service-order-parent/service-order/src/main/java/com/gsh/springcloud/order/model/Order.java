package com.gsh.springcloud.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@Builder
public class Order extends Model<Order> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

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
  private Float price;

  /**
   * 购买数量
   */
  private Integer num;

  /**
   * 用户id
   */
  private Integer userId;

  /**
   * 下单时间
   */
  private Date orderTime;

}
