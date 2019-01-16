package com.gsh.springcloud.member.mongo.dao;

import com.gsh.springcloud.member.mongo.entiy.MongoUser;

public interface MongoUserDao {
    /**

     * 创建对象

     * @param user

     */
    public void saveUser(MongoUser user);

    /**

     * 根据用户名查询对象

     * @param userName

     * @return

     */
    MongoUser findUserByUserName(String userName);

    /**

     * 更新对象

     * @param user

     */
     void updateUser(MongoUser user);

    /**

     * 删除对象

     * @param id

     */
     void deleteUserById(Long id);
}
