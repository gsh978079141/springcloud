package com.gsh.springcloud.servicegood.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.springcloud.serviceorder.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
