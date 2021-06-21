package com.gsh.springcloud.starter.file.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gsh
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "oss.aliyun")
public class AliyunProperties {

  private String accessKeyId;

  private String accessKeySecret;

  private String endpoint;

  private String host;
}
