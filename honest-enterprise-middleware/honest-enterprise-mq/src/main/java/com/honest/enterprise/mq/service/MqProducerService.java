package com.honest.enterprise.mq.service;


import com.honest.enterprise.mq.dto.MqMessageDTO;

/**
 * MQ生产者发送消息服务
 *
 * @author fanjie
 * @date 2021-12-23 10:25
 */
public interface MqProducerService {

    /**
     * 发送消息
     *
     * @param mqMessageDTO
     * @return
     */
    boolean sendMessage(MqMessageDTO mqMessageDTO);
}
