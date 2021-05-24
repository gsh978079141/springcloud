package com.gsh.springcloud.message.config;

import com.gsh.springcloud.message.listener.ConfirmCallbackService;
import com.gsh.springcloud.message.listener.ReturnCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @program: EDU_DBP_APP
 * @description: RabbitMQ 配置类
 * @author: Gsh
 * @create: 2021-03-19 09:45
 **/
@Configuration
@Slf4j
public class RabbitMQConfiguration {

  @Resource
  private Environment env;

  @Resource
  private CachingConnectionFactory connectionFactory;

  @Resource
  private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

  @Resource
  private ConfirmCallbackService confirmCallbackService;

  @Resource
  private ReturnCallbackService returnCallbackService;

  @Bean
  public Queue createRecvMessageTopic(
          @Value("${mqtt.recv-message-topic-prefix}_${node.name}") String queueName) {
    log.info("Create MQ Queue: {}", queueName);
    return new Queue(queueName);
  }

  @Bean
  public Queue createQueryRecognizeTopic(
          @Value("${mqtt.query-recognize-topic-prefix}_${node.name}") String queueName) {
    log.info("Create MQ Queue: {}", queueName);
    return new Queue(queueName);
  }

  /**
   * 单一消费者
   *
   * @return
   */
  @Bean(name = "singleListenerContainer")
  public SimpleRabbitListenerContainerFactory listenerContainer() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    factory.setConcurrentConsumers(1);
    factory.setMaxConcurrentConsumers(1);
    factory.setPrefetchCount(1);
    factory.setTxSize(1);
    factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
    return factory;
  }

  /**
   * 多个消费者
   *
   * @return
   */
  @Bean(name = "multiListenerContainer")
  public SimpleRabbitListenerContainerFactory multiListenerContainer() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factoryConfigurer.configure(factory, connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    factory.setAcknowledgeMode(AcknowledgeMode.NONE);
//    factory.setAcknowledgeMode(env.getProperty("spring.rabbitmq.listener.simple.acknowledge-mode", AcknowledgeMode.class));
    factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency", int.class));
    factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency", int.class));
    factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch", int.class));
    return factory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    connectionFactory.setPublisherConfirms(true);
    connectionFactory.setPublisherReturns(true);
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setConfirmCallback(confirmCallbackService);
    rabbitTemplate.setReturnCallback(returnCallbackService);
    return rabbitTemplate;
  }

}