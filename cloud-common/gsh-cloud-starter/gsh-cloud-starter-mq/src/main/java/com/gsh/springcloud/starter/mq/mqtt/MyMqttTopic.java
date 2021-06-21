package com.gsh.springcloud.starter.mq.mqtt;

import java.lang.annotation.*;

/**
 * @author louis
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMqttTopic {
  String value();
}
