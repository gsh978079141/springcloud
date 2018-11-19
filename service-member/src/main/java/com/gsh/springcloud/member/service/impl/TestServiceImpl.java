package com.gsh.springcloud.member.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.gsh.springcloud.common.entity.User;
import com.gsh.springcloud.member.dao.UserMapper;
import com.gsh.springcloud.member.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TestServiceImpl implements TestService {


    @Autowired
    UserMapper userMapper;

    @TxTransaction
    @Transactional
    @Override
    public int lcnTest()  {
        User user = new User();
        user.setUsername("lcnTest");
        user.setIntegral(20);
        userMapper.insert(user);
        return 12;
    }

    @Transactional
    @Override
    public int localTest() {
        User user = new User();
        user.setUsername("lcnTest");
        user.setIntegral(20);
        userMapper.insertTest(user);
        int a = 1 / 0 ;
        return 12;
    }




}
