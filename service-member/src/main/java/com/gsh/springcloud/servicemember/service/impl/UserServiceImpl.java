package com.gsh.springcloud.servicemember.service.impl;

import com.gsh.springcloud.entity.User;
import com.gsh.springcloud.servicemember.dao.UserMapper;
import com.gsh.springcloud.servicemember.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
