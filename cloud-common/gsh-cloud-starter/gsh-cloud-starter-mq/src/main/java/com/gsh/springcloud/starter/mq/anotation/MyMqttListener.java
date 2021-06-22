package com.gsh.springcloud.starter.mq.anotation;

import java.lang.annotation.*;

/**
 * @author gsh
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMqttListener {
  String topic();
}
