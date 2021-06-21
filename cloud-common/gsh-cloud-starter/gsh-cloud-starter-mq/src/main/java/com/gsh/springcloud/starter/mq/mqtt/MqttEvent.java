package com.gsh.springcloud.starter.mq.mqtt;

import java.lang.annotation.*;

/**
 * @author louis
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqttEvent {
}