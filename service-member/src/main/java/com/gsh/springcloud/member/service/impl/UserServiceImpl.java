package com.gsh.springcloud.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingapi.tx.annotation.TxTransaction;
import com.gsh.springcloud.member.dao.UserMapper;
import com.gsh.springcloud.common.entity.User;
import com.gsh.springcloud.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @TxTransaction
    @Transactional
    @Override
    public int mpLcnTest() {
        User user = new User();
        user.setUserName("lcnTest");
        user.setIntegral(20);
        userMapper.insert(user);
        int a = 1 / 0 ;
        return 12;
    }


    @Transactional
    @Override
    public int mpLocalTest() {
        User user = new User();
        user.setUserName("lcnTest");
        user.setIntegral(20);
        userMapper.updateById(user);
        userMapper.insert(user);
//        user.selectAll();
        int a = 1 / 0 ;
        return 12;
    }




}
