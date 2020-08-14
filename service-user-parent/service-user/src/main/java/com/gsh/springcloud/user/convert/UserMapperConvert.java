package com.gsh.springcloud.user.convert;

import com.gsh.springcloud.user.model.User;
import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperConvert {
  User convert2entity(UserReq req);

  User convert2entity(UserResp resp);

  UserReq convert2req(User entity);

  UserResp convert2resp(User entity);
}
