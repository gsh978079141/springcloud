package com.gsh.springcloud.gateway.keycloak.impl;

import com.google.common.collect.Sets;
import com.gsh.springcloud.gateway.domain.ClientAuthorizationDataDto;
import com.gsh.springcloud.gateway.keycloak.AuthDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @author gsh
 */
@Slf4j
@Component
public class AuthDataRedisCache implements AuthDataCache {

  /**
   * 每个用户数据用单独的key，每个key过期时间单独计算
   * 前缀加上服务名和profile，因为多个环境和服务用同一个redis cluster，避免冲突
   */

  @Value("${spring.application.name}.${spring.profiles.active}.rolePermissions")
  private String rolePermissionsKey;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;


  @Override
  public void refresh(ClientAuthorizationDataDto data) {

    redisTemplate.boundHashOps(rolePermissionsKey).putAll(data.getRolePermissionsMap());
    // 之所以不先直接清空该hash的所有值然后全量更新，是担心清空操作和全量更新操作之间会有查询请求进来，导致取不到数据
    // 所以先覆盖存在的所有role的数据，再检查出无效的role并删除
    Set<String> newRoles = data.getRolePermissionsMap().keySet();
    Set<String> curRoles = redisTemplate.<String, Set<String>>boundHashOps(rolePermissionsKey).keys();
    Set<String> deletingRoles = Sets.newHashSet();
    curRoles.forEach(r -> {
      if (!newRoles.contains(r)) {
        deletingRoles.add(r);
      }
    });
    if (!deletingRoles.isEmpty()) {
      redisTemplate.<String, Set<String>>boundHashOps(rolePermissionsKey).delete(deletingRoles.toArray());
    }
  }


  @Override
  public boolean hasPermissionInRoles(String[] permissions, Set<String> roles) {
    List<Set<String>> permissionSetList = redisTemplate.<String, Set<String>>opsForHash().multiGet(rolePermissionsKey, roles);
    Set<String> requiredPermissionSet = Sets.newHashSet(permissions);
    return permissionSetList.stream().anyMatch(permissionSet -> {
      boolean flag = false;

      // 使用retainAll会改变list1的值，所以写一个替代
      Set<String> templateSet = Sets.newHashSet(permissionSet);
      templateSet.retainAll(requiredPermissionSet);
      // 有交集
      if (templateSet.size() > 0) {
        flag = true;
      }

      return flag;
    });
  }

  @Override
  public void saveRole(String roleName, Set<String> permissions) {
    redisTemplate.opsForHash().put(rolePermissionsKey, roleName, permissions);
  }

  @Override
  public void deleteRole(String roleName) {
    redisTemplate.opsForHash().delete(rolePermissionsKey, roleName);
  }

}
