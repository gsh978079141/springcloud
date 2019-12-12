package entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
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

}
