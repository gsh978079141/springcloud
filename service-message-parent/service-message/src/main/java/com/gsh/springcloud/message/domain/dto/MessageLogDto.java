package com.gsh.springcloud.message.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * (MessageLog)表DTO类
 *
 * @author EasyCode
 */
@ApiModel(description = "")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageLogDto {
  @ApiModelProperty(value = "id")
  private Long id;


  @ApiModelProperty(value = "UUID")
  private String uuid;

  @ApiModelProperty(value = "消息体")
  private String msg;

  @ApiModelProperty(value = "目标队列")
  private String targetTopic;

  @ApiModelProperty(value = "消息状态")
  private String status;

  @ApiModelProperty(value = "备注信息")
  private String remark;

  @ApiModelProperty(value = "发送者服务名称")
  private String senderName;

  @ApiModelProperty(value = "发送时间")
  private Date sendTime;

  @ApiModelProperty(value = "接收者服务名称")
  private String receiverName;

  @ApiModelProperty(value = "接收时间")
  private Date receiveTime;

  @ApiModelProperty(value = "补偿间隔时间(分钟)")
  private Integer compensateIntervalTime;

  @ApiModelProperty(value = "最大重试次数")
  private Integer maxRetryCount;

  @ApiModelProperty(value = "实际重试次数")
  private Integer actualRetryCount;

  @ApiModelProperty(value = "最后一次重试时间")
  private Date lastRetryTime;

  @AllArgsConstructor
  public enum Status {
    READY("待发送", "READY"),
    UNACKED("待应答/待确认", "UNACKED"),
    MANUAL("人工干预", "MANUAL"),
    SUCCESS("发送成功", "SUCCESS");
    private String name;
    private String code;

    public String getCode() {
      return code;
    }

    public String getName() {
      return name;
    }
  }

}