package com.gsh.springcloud.message.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.springcloud.message.domain.entity.MessageLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * (MessageLog)表数据库访问层
 *
 * @author EasyCode
 */
@Mapper
public interface MessageLogMapper extends BaseMapper<MessageLog> {
  int updateRetryInfoByUuid(String uuid);

}