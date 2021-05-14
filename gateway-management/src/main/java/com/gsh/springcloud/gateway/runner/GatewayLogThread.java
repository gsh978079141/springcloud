package com.gsh.springcloud.gateway.runner;

import com.gsh.springcloud.gateway.domain.SysLog;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: wdwh
 * @description: 异步保存日志
 * @author: wh008
 * @create: 2020/07/01 12:23
 */
@Slf4j
public class GatewayLogThread extends Thread {

  private SysLog sysLog;


  public GatewayLogThread(SysLog sysLog) {

    this.sysLog = sysLog;

  }

  @Override
  public void run() {
    log.debug(sysLog.toString());
  }
}
