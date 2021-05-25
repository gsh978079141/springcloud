package com.gsh.springcloud.user.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.springcloud.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)表数据库访问层
 *
 * @author EasyCode
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}