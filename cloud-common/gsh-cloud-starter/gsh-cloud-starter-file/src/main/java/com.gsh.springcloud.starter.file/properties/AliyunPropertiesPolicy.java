package com.gsh.springcloud.starter.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author gsh
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss.aliyun.policy")
public class AliyunPropertiesPolicy {
  private String dir;
  private Integer expires;
  private String bucket;
}
