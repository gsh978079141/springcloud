package com.gsh.springcloud.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rabbit-mq", ignoreUnknownFields = true)
public class RabbitMqProperties {

  private String authDataExchange = "exchange.auth-data-change";

  private String authDataRoutingPrefix = "auth-data-change.";


}
