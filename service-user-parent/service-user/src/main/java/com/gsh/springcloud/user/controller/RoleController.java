package com.gsh.springcloud.user.controller;

import com.gsh.springcloud.user.client.RoleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  @Override
  public void all() {
    log.info("all roles");
  }
}
