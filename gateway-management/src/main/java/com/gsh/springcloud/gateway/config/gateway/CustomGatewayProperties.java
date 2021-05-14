package com.gsh.springcloud.gateway.config.gateway;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 网关自定义属性
 *
 * @author gsh
 */
@ConfigurationProperties(prefix = "gateway", ignoreUnknownFields = true)
@Data
public class CustomGatewayProperties {

  /**
   * 不需要验证token的path
   */
  private String[] ignoredPath;

  /**
   * 不需要验证permission的path
   */
  private String[] ignoredPermissionPath;
}
