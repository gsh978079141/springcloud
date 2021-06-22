package com.gsh.springcloud.starter.mq.anotation;

import java.lang.annotation.*;

/**
 * @author gsh
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqttEvent {
}