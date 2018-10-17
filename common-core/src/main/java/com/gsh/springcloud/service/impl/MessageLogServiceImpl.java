package com.gsh.springcloud.service.impl;

import com.gsh.springcloud.entity.MessageLog;
import com.gsh.springcloud.dao.MessageLogMapper;
import com.gsh.springcloud.service.MessageLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息日志表 服务实现类
 * </p>
 *
 * @author gsh123
 * @since 2018-09-13
 */
@Service
public class MessageLogServiceImpl extends ServiceImpl<MessageLogMapper, MessageLog> implements MessageLogService {

}
