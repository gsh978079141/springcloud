package com.gsh.springcloud.starter.file;

import com.aliyun.oss.OSSClient;
import com.gsh.springcloud.starter.file.properties.AliyunProperties;
import com.gsh.springcloud.starter.file.properties.AliyunPropertiesPolicy;
import com.gsh.springcloud.starter.file.support.impl.FileAliyunServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @author gsh
 */
@Configuration
@EnableConfigurationProperties({AliyunProperties.class, AliyunPropertiesPolicy.class})
@ConditionalOnProperty(prefix = "oss.aliyun", name = "endpoint")
@Import(FileAliyunServiceImpl.class)
public class FileAliyunAutoConfiguration {

  @Resource
  private AliyunProperties aliyunProperties;

  @Bean(destroyMethod = "shutdown")
  @ConditionalOnMissingBean
  public OSSClient ossClient() {
    return new OSSClient(aliyunProperties.getEndpoint(), aliyunProperties.getAccessKeyId(),
            aliyunProperties.getAccessKeySecret());
  }
}
