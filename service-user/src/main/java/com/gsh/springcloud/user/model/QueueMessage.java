package com.gsh.springcloud.user.model;

import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class QueueMessage<T>{
    private String exchange;

    private String queueName;

    private Integer type;

    private Integer group;

    private Date timestamp;

    private T message;

    private String routingKey;

    private Integer status;

    private int retry = 0;

    private int maxRetry = 10;

    private int seconds = 1;


    public QueueMessage() {
        super();
    }

    public QueueMessage(String queueName, T message) {
        super();
        this.queueName = queueName;
        this.exchange = "default.direct.exchange";
        this.type = 10;
        this.group = 10;
        this.timestamp = new Date();
        this.message = message;
        this.status = 10;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
