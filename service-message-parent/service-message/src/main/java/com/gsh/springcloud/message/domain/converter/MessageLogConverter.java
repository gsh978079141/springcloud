package com.gsh.springcloud.message.domain.converter;

import com.gsh.springcloud.message.domain.dto.MessageLogDto;
import com.gsh.springcloud.message.domain.entity.MessageLog;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * (MessageLog)实体转化工具类
 *
 * @author EasyCode
 */
@Mapper(componentModel = "spring")
public interface MessageLogConverter {


  /**
   * convert dto to entity
   */
  MessageLog dto2entity(MessageLogDto dto);

  /**
   * convert entity to dto
   */
  MessageLogDto entity2dto(MessageLog entity);

  /**
   * convert entity list  to dto list
   */
  List<MessageLogDto> entity2dto(List<MessageLog> entities);

  /**
   * convert dto list to entity list
   */
  List<MessageLog> dto2entity(List<MessageLogDto> dtoList);

}