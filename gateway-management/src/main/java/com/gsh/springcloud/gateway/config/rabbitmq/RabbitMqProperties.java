package com.gsh.springcloud.gateway.config.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rabbit-mq", ignoreInvalidFields = true)
public class RabbitMqProperties {

  private String authDataExchange = "exchange.auth-data-change";

  private String authDataQueue = "gateway-management-queue.auth-data-change.edu-management-bff";

  private String authDataRouting = "auth-data-change.edu-management-bff";

}
