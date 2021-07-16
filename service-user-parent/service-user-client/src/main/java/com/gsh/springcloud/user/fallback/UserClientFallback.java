package com.gsh.springcloud.user.fallback;

import com.gsh.springcloud.user.client.UserClient;
import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserClientFallback implements UserClient {

    @Override
    public void login(UserReq user) {
        log.info( "login fallback");
    }

    @Override
    public UserResp find(UserReq user) {
        log.info("find fallback");
        return null;
    }

    @Override
    public void save(UserReq user) {
        log.info("save fallback");
    }

    @Override
    public void updateById(UserReq user) {
        log.info("updateById fallback");
    }

    @Override
    public void deleteById(UserReq user) {
        log.info("deleteById fallback");
    }

    @Override
    public String getHeader(String token) {
        log.info("getHeader fallback");
        return null;
    }

    @Override
    public void test() {
        log.info("test fallback");

    }
}
