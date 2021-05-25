package com.gsh.springcloud.user.domain.converter;

import com.gsh.springcloud.user.domain.dto.UserDto;
import com.gsh.springcloud.user.domain.entity.User;
import com.gsh.springcloud.user.request.UserReq;
import com.gsh.springcloud.user.response.UserResp;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户表(User)实体转化工具类
 *
 * @author EasyCode
 */
@Mapper(componentModel = "spring")
public interface UserConverter {


  /**
   * convert dto to entity
   */
  User dto2entity(UserDto dto);

  /**
   * convert entity to dto
   */
  UserDto entity2dto(User entity);

  /**
   * convert entity list  to dto list
   */
  List<UserDto> entity2dto(List<User> entities);

  /**
   * convert dto list to entity list
   */
  List<User> dto2entity(List<UserDto> dtoList);

  UserResp convert2resp(User entity);

  User convert2entity(UserReq req);

}