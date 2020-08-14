package com.gsh.springcloud.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsh.springcloud.user.client.UserClient;
import com.gsh.springcloud.user.convert.UserMapperConvert;
import com.gsh.springcloud.user.model.User;
import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import com.gsh.springcloud.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: springcloud stream 测试控制器
 * @author: Gsh
 * @create: 2019-12-13 15:55
 **/
@RestController
//@RefreshScope
public class UserControllerTest implements UserClient {

  @Resource
  UserService userService;

  @Resource
  UserMapperConvert userMapperConvert;

  /**
   * 测试 登录发放优惠卷
   *
   * @param userReq
   */
  @Override
  public void login(UserReq userReq) {
    userService.login(userMapperConvert.convert2entity(userReq));
  }

  @Override
  public UserResp find(UserReq userReq) {
    return userMapperConvert.convert2resp(userService.getOne(new QueryWrapper<User>(userMapperConvert.convert2entity(userReq))));
  }

  @Override
  public void save(UserReq userReq) {
    userService.save(userMapperConvert.convert2entity(userReq));
  }

  @Override
  public void updateById(UserReq userReq) {
    userService.updateById(userMapperConvert.convert2entity(userReq));
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
