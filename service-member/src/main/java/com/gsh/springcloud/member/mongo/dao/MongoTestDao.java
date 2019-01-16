package com.gsh.springcloud.member.mongo.dao;

import com.gsh.springcloud.member.mongo.entiy.MongoEntiy;

import java.util.List;

public interface MongoTestDao {
    void saveData(MongoEntiy entity, String collectionName);
    List<MongoEntiy> getAllData(String collectionName);
}
