package com.gsh.springcloud.servicemember.dao;

import com.gsh.springcloud.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
