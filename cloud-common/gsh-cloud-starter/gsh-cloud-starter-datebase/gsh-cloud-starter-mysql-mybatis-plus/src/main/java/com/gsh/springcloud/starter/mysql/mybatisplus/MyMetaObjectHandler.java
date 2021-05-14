package com.gsh.springcloud.starter.mysql.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    //属性名
    this.setFieldValByName("createdTime", new Date(), metaObject);
    this.setFieldValByName("updatedTime", new Date(), metaObject);
    this.setFieldValByName("gmtCreate", new Date(), metaObject);
    this.setFieldValByName("gmtModified", new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.setFieldValByName("updatedTime", new Date(), metaObject);
    this.setFieldValByName("gmtModified", new Date(), metaObject);
  }
}