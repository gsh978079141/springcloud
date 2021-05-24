package com.gsh.springcloud.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsh.springcloud.message.domain.converter.MessageLogConverter;
import com.gsh.springcloud.message.domain.dto.MessageLogDto;
import com.gsh.springcloud.message.domain.entity.MessageLog;
import com.gsh.springcloud.message.domain.mapper.MessageLogMapper;
import com.gsh.springcloud.message.service.MessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (MessageLog)表服务实现类
 *
 * @author gsh
 */
@Service("messageLogService")
@Slf4j
public class MessageLogServiceImpl extends ServiceImpl<MessageLogMapper, MessageLog> implements MessageLogService {

  @Resource
  private MessageLogConverter messageLogConverter;

  @Resource
  private MessageLogMapper messageLogMapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean save(MessageLogDto messageLogDto) {
    log.info("保存消息日志：messageLogDto:{}", JSON.toJSONString(messageLogDto));
    return save(messageLogConverter.dto2entity(messageLogDto));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateByUuid(MessageLogDto messageLogDto, String uuid) {
    log.info("更新消息日志：uuid:{};messageLogDto:{}", uuid, JSON.toJSONString(messageLogDto));
    MessageLog messageLog = messageLogConverter.dto2entity(messageLogDto);
    QueryWrapper<MessageLog> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(MessageLog::getUuid, uuid);
    return update(messageLog, queryWrapper);
  }

  @Override
  public MessageLogDto getOneByUuid(String uuid) {
    return messageLogConverter.entity2dto(lambdaQuery().eq(MessageLog::getUuid, uuid).one());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateRetryInfoByUuid(String uuid) {
    log.info("更新消息日志-重试信息：uuid:{}", uuid);
    int row = messageLogMapper.updateRetryInfoByUuid(uuid);
    if (row <= 0) {
      log.info("更新消息日志-重试信息：uuid:{},达到最大重试次数！", uuid);
      lambdaUpdate().set(MessageLog::getStatus, MessageLogDto.Status.MANUAL.getCode())
              .set(MessageLog::getMsg, "重试失败，请人工干预")
              .eq(MessageLog::getUuid, uuid).update();
      return false;
    } else {
      return true;
    }
  }
}