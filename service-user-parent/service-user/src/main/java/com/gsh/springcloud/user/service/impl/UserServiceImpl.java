package com.gsh.springcloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsh.springcloud.message.client.MessageClient;
import com.gsh.springcloud.message.request.MessageReq;
import com.gsh.springcloud.user.domain.entity.User;
import com.gsh.springcloud.user.domain.mapper.UserMapper;
import com.gsh.springcloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: 用户服务实现类
 * @author: Gsh
 * @create: 2019-12-13 16:03
 **/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Resource
  private MessageClient messageClient;

//  @StreamListener(value = Queues.REGISTER_ISSUE_COUPONS, condition = "headers['index']=='1'")
//  public void receiveByHeader(Message msg) {
//    System.out.println("receive by headers['index']=='1': " + msg);
//  }
//
//  @StreamListener(value = Queues.REGISTER_ISSUE_COUPONS, condition = "headers['index']=='9999'")
//  public void receivePerson(@Payload User user) {
//    System.out.println("receive Person: " + user);
//  }
//
//  @StreamListener(value = Queues.REGISTER_ISSUE_COUPONS)
//  public void receiveAllMsg(String msg) {
//    System.out.println("receive allMsg by StreamListener. content: " + msg);
//  }
//
//  @StreamListener(value = Queues.REGISTER_ISSUE_COUPONS)
//  public void receiveHeaderAndMsg(@Header("index") String index, Message msg) {
//    System.out.println("receive by HeaderAndMsg by StreamListener. content: " + msg);
//  }

  @Override
  public void login(User user) {
    log.info(user.getUserName() + "正在登录,\n*****************开始加积分");
    user = this.getOne(new QueryWrapper<>(user));
    MessageReq messageReq = new MessageReq();
    messageReq.setUserId(user.getId());
    messageReq.setIntegral(user.getIntegral() + 100);
    messageClient.userLoginAddIntegralSend(messageReq);
    log.info("*****************登录成功，加积分成功");
  }

}
