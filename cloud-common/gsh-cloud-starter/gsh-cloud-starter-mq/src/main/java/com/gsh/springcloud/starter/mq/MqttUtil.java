package com.gsh.springcloud.starter.mq;

import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gsh
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
public final class MqttUtil {

  @Resource
  private MqttPushClient MqttPushClient;

  //=============================common============================


}