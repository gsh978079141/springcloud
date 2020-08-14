package com.gsh.springcloud.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
@TableName("coupon")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 名称
   */
  private String couponName;
  /**
   * 折扣
   */
  private double discount;
  /**
   * 状态
   */
  private Integer status;


}
