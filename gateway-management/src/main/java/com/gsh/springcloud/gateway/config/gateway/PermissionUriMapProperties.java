package com.gsh.springcloud.gateway.config.gateway;


import com.gsh.springcloud.gateway.domain.UrlAndPermission;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * @author maj
 */
//@NacosConfigurationProperties(dataId = "${spring.application.name}-${spring.profiles.active}.yaml", prefix ="gateway", autoRefreshed = true)
@ConfigurationProperties(prefix = "gateway-permissions")
@Data
public class PermissionUriMapProperties {

  /**
   * 网关过滤，不截取请求和返回报文
   */
  private List<UrlAndPermission> urlPermission = new ArrayList<>();
}
