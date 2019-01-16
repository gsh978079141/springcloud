package com.gsh.springcloud.member.mongo.daoimpl;

import com.gsh.springcloud.member.mongo.dao.MongoUserDao;
import com.gsh.springcloud.member.mongo.entiy.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author gsh
 * @Title: MongoUserDaoImpl
 * @Package com.gsh.springcloud.member.mongo.daoimpl
 * @Description:
 * @date 2019/1/15 20:42
 */
@Component
public class MongoUserDaoImpl implements MongoUserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param user
     */

    @Override

    public void saveUser(MongoUser user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */

    @Override
    public MongoUser findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        MongoUser user = mongoTemplate.findOne(query, MongoUser.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */

    @Override

    public void updateUser(MongoUser user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, MongoUser.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,MongoUser.class);
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, MongoUser.class);
    }
}
