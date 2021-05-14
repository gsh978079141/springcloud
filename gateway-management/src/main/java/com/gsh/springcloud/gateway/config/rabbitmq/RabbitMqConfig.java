package com.gsh.springcloud.gateway.config.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author maj
 */
@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMqConfig {

  @Resource
  private RabbitMqProperties rabbitMqProperties;

  @Bean
  public TopicExchange authDataExchange() {
    return new TopicExchange(rabbitMqProperties.getAuthDataExchange(), true, false);
  }

  @Bean
  public Queue authDataQueue() {
    return new Queue(rabbitMqProperties.getAuthDataQueue(), true, false, false);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder
            .bind(authDataQueue())
            .to(authDataExchange())
            .with(rabbitMqProperties.getAuthDataRouting());
  }

  /**
   * 解决方法:添加这个类进行序列化解析
   * 会自动识别
   *
   * @param objectMapper json序列化实现类
   * @return mq 消息序列化工具
   */
  @Bean
  public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
  }
}
