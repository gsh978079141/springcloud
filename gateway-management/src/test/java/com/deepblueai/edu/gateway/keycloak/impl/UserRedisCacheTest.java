package com.gsh.springcloud.gateway.keycloak.impl;

import com.gsh.springcloud.gateway.domain.UserDto;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRedisCacheTest {

  @Container
  private static final GenericContainer redisContainer = new GenericContainer(DockerImageName.parse("redis:6.0-alpine"))
          .withExposedPorts(6379);
  @Autowired
  private UserRedisCache userRedisCache;

  @BeforeEach
  void setUp() {


  }

  @AfterEach
  void tearDown() {
  }


  @Test
  void put() {
    UserDto user = UserDto.builder()
            .id("xxasdsadsadsx")
            .username("13000000000")
            .firstName("张三")
            .lastName("")
            .attributes(Maps.newHashMap("school_id", "1"))
            .build();
    userRedisCache.put(user);
    UserDto cachedUser = userRedisCache.get("13000000000");
    assertNotNull(cachedUser);
    assertEquals(user, cachedUser);
  }
}