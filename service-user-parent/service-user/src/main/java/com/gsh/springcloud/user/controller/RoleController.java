package com.gsh.springcloud.user.controller;

import com.gsh.springcloud.starter.mq.config.MqttPushClient;
import com.gsh.springcloud.user.client.RoleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description:
 * @author: Gsh
 * @create: 2021-05-19 11:04
 **/
@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController implements RoleClient {
  @Resource
  private MqttPushClient mqttPushClient;

  @Override
  public void all() {
    mqttPushClient.publish(0, false, "vision-iot/device/gsh/mqtt/test/publish", "message");
    log.info("all roles");
  }
}
