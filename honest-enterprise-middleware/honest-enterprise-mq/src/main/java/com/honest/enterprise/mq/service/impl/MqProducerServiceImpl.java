package com.honest.enterprise.mq.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.honest.enterprise.mq.dto.MqMessageDTO;
import com.honest.enterprise.mq.service.MqProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * MQ生产者发送消息服务
 *
 * @author fanjie
 * @date 2021-12-23 10:25
 */
@Slf4j
@Service
public class MqProducerServiceImpl implements MqProducerService {
    @Autowired(required = false)
    private DefaultMQProducer defaultMQProducer;

    @Override
    public boolean sendMessage(MqMessageDTO mqMessageDTO) {
        log.info("开始发送消息, mqMessageDTO:{}", JSON.toJSONString(mqMessageDTO));
        SendResult sendResult;
        try {
            String json="";
            if(mqMessageDTO.getContent()!=null){
                json= JSONUtil.toJsonStr(mqMessageDTO.getContent());
            }
            Message message = new Message(mqMessageDTO.getTopic(), mqMessageDTO.getTag(), mqMessageDTO.getKey(), json.getBytes());
            //设置延迟等级
            if(mqMessageDTO.getMessageDelayLevel()!=null){
                message.setDelayTimeLevel(mqMessageDTO.getMessageDelayLevel());
            }
            sendResult = defaultMQProducer.send(message);
        } catch (Exception e) {
            log.error("消息发送失败, mqMessageDTO={}, cause:{}", JSON.toJSONString(mqMessageDTO), e);
            return false;
        }
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("发送成功, sendResult:{}", JSON.toJSONString(sendResult));
            mqMessageDTO.setMsgId(sendResult.getMsgId());
            return true;
        }

        return false;
    }
}
