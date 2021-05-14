package com.gsh.springcloud.account.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@EnableWebMvc
@Configuration
@Slf4j
public class ServiceConfiguration {

  @Bean
  @Primary
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setCacheSeconds(3000);
    messageSource.setBasenames("classpath*:messages/messages-account");
    return messageSource;
  }

  @Bean
  public LocaleResolver localeResolver() {
    LocaleResolver localeResolver = new LocaleResolver();
    localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
    return localeResolver;
  }

}
