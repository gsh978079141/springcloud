package com.gsh.springcloud.starter.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
@Slf4j
public class FeignConfiguration {

  @Resource
  ObjectMapper objectMapper;

  @Autowired
  ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  public RequestInterceptor requestMethodHandleInterceptor() {
    return (template) -> {
      // GET request handle
      if (template.method().equals(HttpMethod.GET.name()) && template.body() != null) {
        try {
          JsonNode jsonNode = objectMapper.readTree(template.body());
//          template.body();
          Map<String, Collection<String>> queries = new HashMap<>();
          buildQuery(jsonNode, "", queries);
          log.info("query parameters are::::{}", queries);
          template.queries(queries);
        } catch (IOException e) {
          log.error("error happening when reading request body", e);
        }
      }
    };
  }

  @Bean
  public Retryer retry() {
    // default Retryer will retry 3 times waiting waiting
    // 100 ms per retry with a 1.5* back off multiplier
    return new Retryer.Default(100, SECONDS.toMillis(1), 3);
  }

  @Bean
  public Encoder feignEncoder() {
    return new SpringFormEncoder(new SpringEncoder(messageConverters));
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  private void buildQuery(JsonNode jsonNode, String path, Map<String, Collection<String>> queries) {
    if (!jsonNode.isContainerNode()) {   // 叶子节点
      if (jsonNode.isNull()) {
        return;
      }
      Collection<String> values = queries.get(path);
      if (null == values) {
        values = new ArrayList<>();
        queries.put(path, values);
      }
      values.add(jsonNode.asText());
      return;
    }
    if (jsonNode.isArray()) {   // 数组节点
      Iterator<JsonNode> it = jsonNode.elements();
      while (it.hasNext()) {
        buildQuery(it.next(), path, queries);
      }
    } else {
      Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
      while (it.hasNext()) {
        Map.Entry<String, JsonNode> entry = it.next();
        if (StringUtils.hasText(path)) {
          buildQuery(entry.getValue(), path + "." + entry.getKey(), queries);
        } else {  // 根节点
          buildQuery(entry.getValue(), entry.getKey(), queries);
        }
      }
    }
  }

  //  @Bean
//  public RequestInterceptor oauth2FeignRequestInterceptor() {
//    return new OAuth2FeignRequestInterceptor(feignOAuth2ClientContext,
//            clientCredentialsResourceDetails);
//  }
//
//  @Bean
//  public OAuth2RestTemplate oauth2RestTemplate() {
//    OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(
//            clientCredentialsResourceDetails, feignOAuth2ClientContext);
//    log.debug("Begin OAuth2RestTemplate: getAccessToken");
//    /* To validate if required configurations are in place during startup */
////    oAuth2RestTemplate.getAccessToken();
//    log.debug("End OAuth2RestTemplate: getAccessToken");
//    return oAuth2RestTemplate;
//  }
}
