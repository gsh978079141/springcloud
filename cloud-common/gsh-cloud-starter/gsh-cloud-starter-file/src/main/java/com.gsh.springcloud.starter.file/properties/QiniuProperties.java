package com.gsh.springcloud.starter.file.properties;

import com.gsh.springcloud.starter.file.constants.QiniuZone;
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
@ConfigurationProperties(prefix = "cdn.qiniu")
public class QiniuProperties {

  private String accesskey = "UNKNOWN";

  private String accessSecret = "UNKNOWN";

  private QiniuZone zoneName = QiniuZone.HUADONG;

  private String bucketName = "UNKNOWN";

  private String accessRootUrl = "UNKNOWN";

}
