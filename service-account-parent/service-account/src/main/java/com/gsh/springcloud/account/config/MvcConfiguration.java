package com.gsh.springcloud.account.config;

import com.gsh.springcloud.starter.web.support.GeneralErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfiguration {

  @Resource
  ServerProperties serverProperties;

  @Resource
  List<ErrorViewResolver> errorViewResolvers;


  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("Authorization", "Origin", "X-Requested-With", "Content-Type", "mode")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600);
      }

      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
      }

      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
      }
    };
  }

  @Bean
  @Primary
  public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
    return new GeneralErrorController(errorAttributes, this.serverProperties.getError(), this.errorViewResolvers);
  }


}
