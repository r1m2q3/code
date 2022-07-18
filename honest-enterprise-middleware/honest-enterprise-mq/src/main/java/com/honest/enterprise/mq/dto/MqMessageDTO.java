package com.honest.enterprise.mq.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * @author fanjie
 * @date 2021-12-22
 */
@Data
public class MqMessageDTO <T>  implements Serializable {

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 主题
     */
    private String topic;

    /**
     * 消息标签体
     */
    private String tag;

    /**
     * 消息业务主键
     */
    private String key;
    /**
     *  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 分别对应下面的延迟时间，在使用时，直接传递 level即可。
     *  messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    private Integer messageDelayLevel;

    /**
     * 消息体
     */
    private  T  content;


    public MqMessageDTO() {
    }

    public MqMessageDTO(String msgId, String topic, String tag, String key, T content) {
        this.msgId = msgId;
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.content = content;
    }
}
