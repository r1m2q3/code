package com.honest.enterprise.core.model;

import lombok.Data;

import java.util.Date;

/**
 * @author fanjie
 * @date 2022-07-17
 */
@Data
public class MsgDTO<T>{
    /**
     * 消息ID
     */
    private String msgId;
    /**
     * 消息发送时间
     */
    private Date sendTime;

    /**
     * 业务数据
     */
    private T data;

    public static <T> MsgDTO<T> createMsg(String msgId,T data) {
        MsgDTO<T> domainEvent = new MsgDTO<>();
        domainEvent.setMsgId(msgId);
        domainEvent.setData(data);
        domainEvent.setSendTime(new Date());
        return domainEvent;
    }
}
