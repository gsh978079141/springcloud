package com.gsh.springcloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsh.springcloud.user.domain.entity.User;

public interface UserService extends IService<User> {
  /**
   * 测试
   * 登录加积分
   *
   * @param user
   */
  void login(User user);

}
