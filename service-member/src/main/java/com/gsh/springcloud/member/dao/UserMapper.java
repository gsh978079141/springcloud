package com.gsh.springcloud.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.springcloud.common.entity.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
public interface UserMapper extends BaseMapper<User> {
    boolean insertTest(User user);
}
