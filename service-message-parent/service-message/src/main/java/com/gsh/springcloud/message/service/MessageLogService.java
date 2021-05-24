package com.gsh.springcloud.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsh.springcloud.message.domain.dto.MessageLogDto;
import com.gsh.springcloud.message.domain.entity.MessageLog;

/**
 * (Message)表服务接口
 *
 * @author EasyCode
 */
public interface MessageLogService extends IService<MessageLog> {

  boolean save(MessageLogDto messageLogDto);

  boolean updateByUuid(MessageLogDto messageLogDto, String uuid);

  MessageLogDto getOneByUuid(String uuid);

  boolean updateRetryInfoByUuid(String uuid);
}