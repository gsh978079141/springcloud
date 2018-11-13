package com.gsh.springcloud.serviceorder.service;

import com.gsh.springcloud.serviceorder.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
    * @Title: OrderFeginClient
    * @Package com.gsh.springcloud.service
    * @Description: service-member 为已存在的服务名
    * @author gsh
    * @date 2018/9/21 10:18
    */
@FeignClient("service-member")
public interface OrderFeginClient {
    /**例
     *获取列表
     * @return
     */
    @GetMapping("/member/getList.do")
    List<String> getInfo();

    /**例
     *参数传参
     * @param id
     * @return
     */
    @GetMapping("/user/findById")
    User findById(@RequestParam("id") Integer id);

    /**例
     *body传参 user 实体
     * @param user
     * @return
     */
    @PostMapping("/user/findList")
    List<User> findList(@RequestBody User user);

    /**例
     *头部传参
     * @param token
     * @return
     */
    @GetMapping("/user/getHeader")
    String getHeader(@RequestHeader("token") String token);

    /**
     * lcn分布式事务测试
     * @return
     */
    boolean lcnTest();
}
