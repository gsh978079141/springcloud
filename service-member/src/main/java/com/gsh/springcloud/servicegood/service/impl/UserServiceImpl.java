package com.gsh.springcloud.servicegood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsh.springcloud.serviceorder.entity.User;
import com.gsh.springcloud.servicegood.dao.UserMapper;
import com.gsh.springcloud.servicegood.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
