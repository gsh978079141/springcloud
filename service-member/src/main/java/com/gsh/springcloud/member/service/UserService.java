package com.gsh.springcloud.member.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsh.springcloud.common.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gsh123
 * @since 2018-09-11
 */
public interface UserService extends IService<User> {
    int mpLcnTest();
    int mpLocalTest();
}
