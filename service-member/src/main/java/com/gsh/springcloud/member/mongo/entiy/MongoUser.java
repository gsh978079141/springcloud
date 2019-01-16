package com.gsh.springcloud.member.mongo.entiy;

import lombok.Data;

import java.io.Serializable;
@Data
public class MongoUser implements Serializable {
    private Long id;
    private String userName;
    private String passWord;
}
