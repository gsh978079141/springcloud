package com.gsh.springcloud.starter.file;

import com.gsh.springcloud.starter.file.properties.QiniuProperties;
import com.gsh.springcloud.starter.file.support.impl.FileQiniuServiceImpl;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(QiniuProperties.class)
@ConditionalOnProperty(prefix = "cdn.qiniu", name = "zone-name")
@Import(FileQiniuServiceImpl.class)
public class FileQiniuAutoConfiguration {

  @Resource
  QiniuProperties qiniuProperties;

  @Bean
  public Auth qiniuAuth() {
    return Auth.create(qiniuProperties.getAccesskey(), qiniuProperties.getAccessSecret());
  }

  @Bean
  public Zone qiniuZone() {
    switch (qiniuProperties.getZoneName()) {
      case HUADONG:
        return Zone.huadong();
      case BEIMEI:
        return Zone.beimei();
      case HUABEI:
        return Zone.huabei();
      case HUANAN:
        return Zone.huanan();
      case XINJIAPO:
        return Zone.xinjiapo();
      default:
        return Zone.huadong();
    }
  }

  @Bean
  public Configuration qiniuConfiguration(@Qualifier("qiniuZone") Zone qiniuZone) {
    return new Configuration(qiniuZone);
  }

  @Bean
  public UploadManager qiniuUploadManager(
          @Qualifier("qiniuConfiguration") Configuration qiniuConfiguration) {
    return new UploadManager(qiniuConfiguration);
  }

  @Bean
  public BucketManager qiniuBucketManager(
          @Qualifier("qiniuAuth") Auth qiniuAuth,
          @Qualifier("qiniuConfiguration") Configuration qiniuConfiguration) {
    return new BucketManager(qiniuAuth, qiniuConfiguration);
  }

  @Bean
  public OperationManager qiniuOperationManager(
          @Qualifier("qiniuAuth") Auth qiniuAuth,
          @Qualifier("qiniuConfiguration") Configuration qiniuConfiguration) {
    return new OperationManager(qiniuAuth, qiniuConfiguration);
  }

}
