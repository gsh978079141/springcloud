package com.gsh.springcloud.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * (Order)表DTO类
 *
 * @author EasyCode
 */
@ApiModel(description = "")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "订单名称")
  private String name;

  @ApiModelProperty(value = "订单号")
  private String orderNo;

  @ApiModelProperty(value = "价格")
  private Double price;

  @ApiModelProperty(value = "购买数量")
  private Integer num;

  @ApiModelProperty(value = "用户id")
  private Long userId;

  @ApiModelProperty(value = "下单时间")
  private Date orderTime;

}