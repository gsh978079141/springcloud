package com.gsh.springcloud.starter.mq.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 事件相关类
 *
 * @author gsh
 */
@Data
@Component
@AllArgsConstructor
public class Event {
  private String name;
  private IEventHandler handler;
}
