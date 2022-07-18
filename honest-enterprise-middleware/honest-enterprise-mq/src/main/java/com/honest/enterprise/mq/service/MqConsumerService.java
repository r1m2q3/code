package com.honest.enterprise.mq.service;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;

import java.util.Date;

/**
 * @author fanjie
 * @date 2021-12-23 10:47
 */
public interface MqConsumerService {

    /**
     * 前置
     *
     * @param message
     * @return
     */
    ConsumeConcurrentlyStatus beforeHandler(String message);

    /**
     * 消息处理
     *
     * @param message
     * @return
     */
    ConsumeConcurrentlyStatus handle(String message) throws Exception;

    /**
     * 后置
     *
     * @param message
     * @param startHandlerTime
     * @param status
     */
    void afterHandler(String message, Date startHandlerTime, ConsumeConcurrentlyStatus status);
}
