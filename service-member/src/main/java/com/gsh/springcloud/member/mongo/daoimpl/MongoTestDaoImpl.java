package com.gsh.springcloud.member.mongo.daoimpl;

import com.gsh.springcloud.member.mongo.dao.MongoTestDao;
import com.gsh.springcloud.member.mongo.entiy.MongoEntiy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("mongoTestDao")
@Slf4j
public class MongoTestDaoImpl implements MongoTestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveData(MongoEntiy entity, String collectionName) {
        mongoTemplate.save(entity, collectionName);
        log.info("save snmp data in {} succeed.", collectionName);
    }

    @Override
    public List<MongoEntiy> getAllData(String collectionName) {
        return mongoTemplate.findAll(MongoEntiy.class, collectionName);
    }
}
