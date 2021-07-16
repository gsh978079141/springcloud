package com.gsh.springcloud.user.config;

import com.gsh.springcloud.user.fallback.UserClientFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
  @Bean
  public UserClientFallback echoServiceFallback() {
    return new UserClientFallback();
  }
}
