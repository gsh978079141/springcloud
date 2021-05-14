package com.gsh.springcloud.starter.web;

import com.gsh.springcloud.starter.web.constants.MvcConstants;
import com.gsh.springcloud.starter.web.support.FeignErrorDecoder;
import com.gsh.springcloud.starter.web.support.GeneralErrorController;
import com.gsh.springcloud.starter.web.support.GeneralExceptionHandler;
import com.gsh.springcloud.starter.web.support.HttpRequestPrinterFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsh
 */

@Import({
        GeneralExceptionHandler.class,
//        FeignConfiguration.class,
        FeignErrorDecoder.class
})
@Configuration
@EnableWebMvc
@Slf4j
public class WebAutoConfiguration implements WebMvcConfigurer {


  @Resource
  ServerProperties serverProperties;


  @Resource
  List<ErrorViewResolver> errorViewResolvers;


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping(MvcConstants.URL_PATTERN_ALL_MATCH)
            .allowedOrigins(MvcConstants.PATTERN_ALL_MATCH)
            .allowedMethods(MvcConstants.ALLOW_METHODS)
            .allowedHeaders(MvcConstants.ALLOW_HEADERS)
            .exposedHeaders(MvcConstants.HEADER_COOKIE)
            .allowCredentials(true);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(MvcConstants.SWAGGER_UI_URI)
            .addResourceLocations(MvcConstants.SWAGGER_RESOURCE_LOCATION);
    if (!registry.hasMappingForPattern(MvcConstants.URL_PATTERN_WEBJARS)) {
      registry.addResourceHandler(MvcConstants.URL_PATTERN_WEBJARS)
              .addResourceLocations(MvcConstants.WEBJARS_RESOURCE_LOCATION);
    }
    if (!registry.hasMappingForPattern(MvcConstants.URL_PATTERN_ALL_MATCH)) {
      registry.addResourceHandler(MvcConstants.URL_PATTERN_ALL_MATCH)
              .addResourceLocations(MvcConstants.RESOURCE_LOCATIONS);
    }

  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/" + MvcConstants.SWAGGER_UI_URI);
  }

  @Bean
  public CommonsMultipartResolver multipartResolver() {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    return multipartResolver;
  }


  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

    //设置中文编码格式
    mappingJackson2HttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
    mappingJackson2HttpMessageConverter.setPrettyPrint(true);
    List<MediaType> supportedMediaTypes = new ArrayList<>();
//    supportedMediaTypes.add(MediaType.TEXT_HTML);
    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
    supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    supportedMediaTypes.add(MediaType.valueOf("application/*+json"));
    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
    return mappingJackson2HttpMessageConverter;
  }

  @Bean
  @Primary
  public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
    return new GeneralErrorController(errorAttributes, this.serverProperties.getError(),
            this.errorViewResolvers);
  }

//  @Bean
//  public HttpMessageConverter fastJsonHttpMessageConverter() {
//    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//    // 创建配置类
//    FastJsonConfig config = new FastJsonConfig();
//    config.setCharset(StandardCharsets.UTF_8);
//    config.setSerializerFeatures(
//            SerializerFeature.QuoteFieldNames,
//            SerializerFeature.WriteNullListAsEmpty,
//            SerializerFeature.WriteMapNullValue,
//            SerializerFeature.WriteNullStringAsEmpty,
//            SerializerFeature.DisableCircularReferenceDetect,
//            SerializerFeature.WriteNonStringKeyAsString
//    );
//
//    //此处是全局处理方式
//    fastConverter.setDefaultCharset(StandardCharsets.UTF_8);
//    fastConverter.setFastJsonConfig(config);
//    List<MediaType> supportedMediaTypes = new ArrayList<>();
//    supportedMediaTypes.add(MediaType.TEXT_HTML);
//    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//    supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//    supportedMediaTypes.add(MediaType.valueOf("application/*+json"));
//    fastConverter.setSupportedMediaTypes(supportedMediaTypes);
//    return fastConverter;
//  }


//  @Override
//  public void extendMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
//    List<MediaType> supportedMediaTypes = new ArrayList<>();
//    StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//    supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//    stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//    messageConverters.add(stringHttpMessageConverter);
//  }


  @Bean
  @Primary
  public ErrorController customErrorController(ErrorAttributes errorAttributes) {
    return new GeneralErrorController(errorAttributes, this.serverProperties.getError(), this.errorViewResolvers);
  }


  /**
   * 注册自定义过滤器
   *
   * @return
   */
  @ConditionalOnProperty(value = "sys.http-log.enable", havingValue = "true")
  @Bean
  public FilterRegistrationBean<HttpRequestPrinterFilter> httpRequestPrinterFilter() {
    FilterRegistrationBean<HttpRequestPrinterFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    // 设置filter
    filterRegistrationBean.setFilter(new HttpRequestPrinterFilter());
    // 拦截规则
    filterRegistrationBean.addUrlPatterns("/*");
    return filterRegistrationBean;
  }

}
