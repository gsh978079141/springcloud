package com.gsh.springcloud.order.domain.converter;

import com.gsh.springcloud.order.domain.entity.Order;
import com.gsh.springcloud.order.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * (Order)实体转化工具类
 *
 * @author EasyCode
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {


  /**
   * convert dto to entity
   */
  Order dto2entity(OrderDto dto);

  /**
   * convert entity to dto
   */
  OrderDto entity2dto(Order entity);

  /**
   * convert entity list  to dto list
   */
  List<OrderDto> entity2dto(List<Order> entities);

  /**
   * convert dto list to entity list
   */
  List<Order> dto2entity(List<OrderDto> dtoList);

}