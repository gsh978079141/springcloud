package com.gsh.springcloud.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import com.gsh.springcloud.user.client.UserClient;
import com.gsh.springcloud.user.domain.converter.UserConverter;
import com.gsh.springcloud.user.domain.entity.User;
import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import com.gsh.springcloud.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: springcloud stream 测试控制器
 * @author: Gsh
 * @create: 2019-12-13 15:55
 **/
@RestController
@RequestMapping("/users")
public class UserControllerTest implements UserClient {

  @Resource
  private UserService userService;

  @Resource
  private UserConverter userConverter;

  @Resource
  private MqttPushClient mqttPushClient;

  /**
   * 测试 登录发放优惠卷
   *
   * @param userReq
   */
  @Override
  public void login(UserReq userReq) {
    userService.login(userConverter.convert2entity(userReq));
  }

  @Override
  public UserResp find(UserReq userReq) {
    mqttPushClient.publish(0, false, "test", "message");
    return userConverter.convert2resp(userService.getOne(new QueryWrapper<User>(userConverter.convert2entity(userReq))));
  }

  @Override
  public void save(UserReq userReq) {
    userService.save(userConverter.convert2entity(userReq));
  }

  @Override
  public void updateById(UserReq userReq) {
    userService.updateById(userConverter.convert2entity(userReq));
  }

  @Override
  public void deleteById(UserReq userReq) {
    userService.removeById(userReq.getId());
  }

  @Override
  public String getHeader(String token) {
    return null;
  }


}
