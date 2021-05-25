package com.gsh.springcloud.order.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.springcloud.order.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Order)表数据库访问层
 *
 * @author EasyCode
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}