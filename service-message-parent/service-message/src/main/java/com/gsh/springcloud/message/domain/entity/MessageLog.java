package com.gsh.springcloud.message.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * (MessageLog)表实体类
 *
 * @author EasyCode
 */
@Data
@SuperBuilder
@TableName("message_log")
@AllArgsConstructor
@NoArgsConstructor
public class MessageLog implements Serializable {

  private static final long serialVersionUID = -1;

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * UUID
   */
  private String uuid;

  /**
   * 消息体
   */
  private String msg;

  /**
   * 目标队列
   */
  private String targetTopic;

  /**
   * 消息状态
   */
  private String status;

  /**
   * 备注信息
   */
  private String remark;

  /**
   * 发送者服务名称
   */
  private String senderName;

  /**
   * 发送时间
   */
  private Date sendTime;

  /**
   * 接收者服务名称
   */
  private String receiverName;

  /**
   * 接收时间
   */
  private Date receiveTime;

  /**
   * 补偿间隔时间(分钟)
   */
  private Integer compensateIntervalTime;

  /**
   * 最大重试次数
   */
  private Integer maxRetryCount;

  /**
   * 实际重试次数
   */
  private Integer actualRetryCount;

  /**
   * 最后一次重试时间
   */
  private Date lastRetryTime;

}