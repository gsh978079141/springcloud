package com.gsh.springcloud.order.elmodel;

import com.gsh.springcloud.user.request.UserReq;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @program: springcloud
 * @description: Elasticsearch测试实体
 * @author: Gsh
 * @create: 2020-06-03 13:47
 **/
@Document(indexName = "ela_model", type = "ela_model")
@Data
public class ElaModel {
  private Integer id;
  private String name;
  private double tall;
  private Integer age;
  @Field(type = FieldType.Object)
  private UserReq user;
}
