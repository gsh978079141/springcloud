package com.gsh.springcloud.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 消息日志表
 * </p>
 *
 * @author gsh123
 * @since 2018-09-13
 */
@TableName("t_message_log")
public class MessageLog extends Model<MessageLog> {

    private static final long serialVersionUID = 1L;

    /**
     * id

     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 消息id
     */
    @TableField("message_id")
    private String messageId;
    /**
     * 消息体
     */
    private String message;
    /**
     * 发送方
     */
    private String sender;
    /**
     * 接收方
     */
    private String receiver;
    /**
     * 消息投递状态0投递中，1投递成功，2投递失败
     */
    private String msgstatus;
    /**
     * 重试次数
     */
    @TableField("try_count")
    private Integer tryCount;
    /**
     * 下一次重试时间
     */
    @TableField("next_retry")
    private Date nextRetry;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("modify_time")
    private Date modifyTime;
    /**
     * 记录状态1整除0删除
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsgstatus() {
        return msgstatus;
    }

    public void setMsgstatus(String msgstatus) {
        this.msgstatus = msgstatus;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageLog{" +
        ", id=" + id +
        ", messageId=" + messageId +
        ", message=" + message +
        ", sender=" + sender +
        ", receiver=" + receiver +
        ", msgstatus=" + msgstatus +
        ", tryCount=" + tryCount +
        ", nextRetry=" + nextRetry +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        ", status=" + status +
        "}";
    }
}
