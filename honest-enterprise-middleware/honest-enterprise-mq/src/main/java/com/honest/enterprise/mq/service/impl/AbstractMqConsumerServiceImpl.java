package com.honest.enterprise.mq.service.impl;


import com.honest.enterprise.mq.service.MqConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author fanjie
 * @date 2021-12-23 10:47
 */
@Slf4j
@Service("mqConsumerService")
public abstract class AbstractMqConsumerServiceImpl implements MqConsumerService {
    @Override
    public ConsumeConcurrentlyStatus beforeHandler(String message) {
        log.info("mq消息前置处理: message={}", message);
        return null;
    }

    @Override
    public ConsumeConcurrentlyStatus handle(String message) throws Exception {
        log.info("mq消息处理开始，message={}", message);
        try {
            log.info(message);
            boolean ret=onMessage(message);
            if(!ret){
                log.info("message consume fail!");
               // return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                throw new Exception("message consume fail!");
            }
        } catch (Exception e) {
            //log.error("handler error:{}", e);
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            throw e;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public void afterHandler(String message, Date startHandlerTime, ConsumeConcurrentlyStatus status) {
        log.info("mq消息后置处理, status", status);

    }

    /**
     * 接受消息
     * @param message
     * @return
     */
    protected abstract boolean onMessage(String message);
}
