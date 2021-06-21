package com.gsh.springcloud.starter.mq.mqtt;

import java.util.List;

/**
 * 事件处理接口
 *
 * @author gsh
 */
public interface IEventHandler {
  void handle(List<?> params) throws Exception;
}
