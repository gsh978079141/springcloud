package com.gsh.springcloud.order.config;


import com.gsh.springcloud.common.i18n.LocaleResolver;
import com.gsh.springcloud.starter.web.mvc.AnnotationHandlerMappingPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
@Import({
        UpdateOperationInterceptor.class,
        AnnotationHandlerMappingPostProcessor.class,
})
@Slf4j
public class ServiceConfiguration {

  @Bean
  @Primary
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setCacheSeconds(3000);
    messageSource.setBasenames("classpath:messages/messages-order");
    return messageSource;
  }


  @Bean
  public LocaleResolver localeResolver() {
    LocaleResolver localeResolver = new LocaleResolver();
    localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
    return localeResolver;
  }

}
