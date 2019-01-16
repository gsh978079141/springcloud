package com.gsh.springcloud.member.mongo.Controller;

import com.gsh.springcloud.member.mongo.dao.MongoTestDao;
import com.gsh.springcloud.member.mongo.dao.MongoUserDao;
import com.gsh.springcloud.member.mongo.entiy.MongoEntiy;
import com.gsh.springcloud.member.mongo.entiy.MongoUser;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mongo")
public class MongoTestController {

    @Autowired
    private MongoTestDao mongoTestDao;

    @Autowired
    private MongoUserDao mongoUserDao;
    @RequestMapping("/save")
    public  String saveData(){
        List<Document> entrys = new ArrayList<>();
        Document entry = new Document();
        entry.put("ifIndex", 1);
        entry.put("ifInOctets", 586888423);
        entry.put("ifOutOctets", 1054861105);
        entry.put("ifInUcastPkts", 24821672);
        entry.put("ifOutUcastPkts", 15946353);
        entry.put("ifSpeed", 1000000000);
        entry.put("ifMtu", 1514);
        entrys.add(entry);

        MongoEntiy entity = new MongoEntiy();
        entity.setId(ObjectId.get());
        entity.setIfEntiy(entrys);
        entity.setIfRecvTime(LocalDateTime.now().plusHours(8));
        entity.setIfNumber(49);
        mongoTestDao.saveData(entity, "20180619");
        return "success";
    }

    @RequestMapping("/list")
    public List<MongoEntiy> list(){
        List<MongoEntiy> entitys = mongoTestDao.getAllData("20180619");
        for (MongoEntiy entity : entitys) {
            LocalDateTime ifRecvTime = entity.getIfRecvTime();
            System.out.println(ifRecvTime + ": ");
            List<Document> ifEntrys = entity.getIfEntiy();
            for (Document ifEntry : ifEntrys) {
                Integer ifIndex = ifEntry.getInteger("ifIndex");
//                Long ifInOctets = ifEntry.getLong("ifInOctets");
//                Long ifOutOctets = ifEntry.getLong("ifOutOctets");
                System.out.println("ifIndex = " + ifIndex);
//                System.out.println("ifInOctets = " + ifInOctets);
//                System.out.println("ifOutOctets = " + ifOutOctets);
            }
        }
        return entitys;
    }

    @RequestMapping("/listUser")
    public void testSaveUser() throws Exception {
        MongoUser user=new MongoUser();
        user.setId(2L);
        user.setUserName("小明");
        user.setPassWord("fffooo123");
        mongoUserDao.saveUser(user);
    }

    @RequestMapping("/findUserByUserName")
    public void findUserByUserName(){
        MongoUser user= mongoUserDao.findUserByUserName("小明");
    }

    @RequestMapping("/updateUser")
    public void updateUser(){
        MongoUser user=new MongoUser();
        user.setId(2L);
        user.setUserName("天空");
        user.setPassWord("fffxxxx");
        mongoUserDao.updateUser(user);
    }

    @RequestMapping("/deleteUserById")
    public void deleteUserById(){
        mongoUserDao.deleteUserById(1L);
    }

}
