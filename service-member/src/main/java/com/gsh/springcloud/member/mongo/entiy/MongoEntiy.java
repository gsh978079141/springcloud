package com.gsh.springcloud.member.mongo.entiy;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
/**
    * @Title: MongoEntiy
    * @Package com.gsh.springcloud.member.entiy
    * @Description: Mongodb测试实体
    * @author gsh
    * @date 2019/1/15 16:46
    */
@Document
@Data
public class MongoEntiy implements Serializable {
    @Id
    private Object id;
    @Field("ifRecvTime")
    private LocalDateTime ifRecvTime;
    @Field("ifEntiy")
    private List<org.bson.Document> ifEntiy;
    @Field("ifNumber")
    private Integer ifNumber;

}
