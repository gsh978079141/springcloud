package com.gsh.springcloud.gateway.keycloak.impl;

import com.gsh.springcloud.gateway.domain.UserDto;
import com.gsh.springcloud.gateway.keycloak.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author gsh
 */
@Slf4j
@Component("userCache")
public class UserRedisCache implements UserCache {

  /**
   * 每个用户数据用单独的key，每个key过期时间单独计算
   * 前缀加上服务名和profile，因为多个环境和服务用同一个redis cluster，避免冲突
   */
  @Value("${spring.application.name}.${spring.profiles.active}.user.")
  private String userKeyPrefix;

  @Resource
  private RedisTemplate<String, UserDto> redisTemplate;


  public UserRedisCache() {
    log.info("UserRedisCache is registered...");
  }


  @Override
  public UserDto get(String username) {
    String key = userKeyPrefix + username;
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void put(UserDto user) {
    String key = userKeyPrefix + user.getUsername();
    redisTemplate.opsForValue().set(key, user, Duration.ofSeconds(EXPIRE_SECONDS));
  }

}
