package com.gsh.springcloud.starter.mq.mqtt;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件触发类
 *
 * @author gsh
 */
@Component
public class EventEmitter {
  private List<Event> eventList = new ArrayList<>();

  public void on(String eventName, IEventHandler iEventHandler) {
    if (eventName == null || iEventHandler == null) {
      throw new IllegalArgumentException("event is invalid");
    }
    eventList.add(new Event(eventName, iEventHandler));
  }

  public void emit(String eventName, List<Object> params) throws Exception {
    for (Event event : eventList) {
      if (event.getName().equals(eventName)) {
        event.getHandler().handle(params);
      }
    }
  }
}
