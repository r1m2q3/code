package com.honest.enterprise.mq.config;


import com.honest.enterprise.core.utils.IocContextUtils;
import com.honest.enterprise.mq.annotation.AnnMqTopic;
import com.honest.enterprise.mq.annotation.StrategyMqContext;
import com.honest.enterprise.mq.properties.MqConsumerProperties;
import com.honest.enterprise.mq.service.MqConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanjie
 * @date 2021-12-23 10:47
 */
@Slf4j
@Configuration
public class DefaultConsumerBroadcastConfig {
    @Autowired
    private IocContextUtils iocContextUtils;
    @Autowired
    private MqConsumerProperties consumerProperties;
    @Autowired
    private StrategyMqContext strategyMqContext;
    @Autowired
    private RedissonClient redissonClient;


    @Bean
    //@Primary
    public DefaultMQPushConsumer defaultBroadConsumer() throws MQClientException {
        log.info("defaultConsumer 正在创建---------------------------------------");
        //消費者参数设置
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerProperties.getGroupName() + "-Broadcast");
        if (consumerProperties == null || StringUtils.isEmpty(consumerProperties.getNamesrvAddr())) {
            log.info("not rocket mq config info");
            return null;
        }
        consumer.setNamesrvAddr(consumerProperties.getNamesrvAddr());
        consumer.setConsumeThreadMin(consumerProperties.getConsumeThreadMin());
        consumer.setConsumeThreadMax(consumerProperties.getConsumeThreadMax());
        consumer.setConsumeMessageBatchMaxSize(consumerProperties.getConsumeMessageBatchMaxSize());
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //开启内部类实现监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return DefaultConsumerBroadcastConfig.this.onMessage(msgs);
            }
        });
        //设置consumer第一次启动是从队列头部开始还是队列尾部开始  如果不是第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //获取AnnMqTopic 注解的所有消费者类
        Map<String, Object> consumeMap = iocContextUtils.getClassMapByAnn(AnnMqTopic.class);
        if (consumeMap == null || consumeMap.size() == 0) {
            return consumer;
        }
        //所有实现AnnMqTopic的类进行订阅 设置
        Map<String, String> consumeMapGroupTopic = new HashMap<>();
        for (Map.Entry<String, Object> entry : consumeMap.entrySet()) {
            Class<?> strategyClass = entry.getValue().getClass();
            if (!strategyClass.getAnnotation(AnnMqTopic.class).messageModel().equals(MessageModel.BROADCASTING)) {
                continue;
            }

            String topic = strategyClass.getAnnotation(AnnMqTopic.class).topic();
            String tag = strategyClass.getAnnotation(AnnMqTopic.class).tag();
            if (!consumeMapGroupTopic.containsKey(topic)) {
                consumeMapGroupTopic.put(topic, tag);
            } else {
                String tags = consumeMapGroupTopic.get(topic) + " || " + tag;
                consumeMapGroupTopic.put(topic, tags);
            }

        }
        // 支持多个tag, 如 tag1 || tag2 || tag3
        for (Map.Entry<String, String> entry : consumeMapGroupTopic.entrySet()) {
            consumer.subscribe(entry.getKey(), entry.getValue());
        }
        consumer.start();
        return consumer;
    }

    /**
     * 处理body的业务
     *
     * @param msgs
     * @return
     */
    public ConsumeConcurrentlyStatus onMessage(List<MessageExt> msgs) {
        for (MessageExt msg : msgs) {
            String topicTag = msg.getTopic() + ":" + msg.getTags();
            ConsumeConcurrentlyStatus consumerStatus = null;
            try {
                Object serviceBean = strategyMqContext.getStrategyImpl(MqConsumerService.class, topicTag);
                if (serviceBean == null) {
                    break;
                }
                if (serviceBean instanceof MqConsumerService) {

                    String message = new String(msg.getBody(), StandardCharsets.UTF_8);
                    MqConsumerService consumerService = (MqConsumerService) serviceBean;
                    // 预处理
                    consumerService.beforeHandler(message);
                    // 处理
                    consumerStatus = consumerService.handle(message);
                    // 处理之后
                    consumerService.afterHandler(message, new Date(), consumerStatus);

                    return consumerStatus;

                }
            } catch (Exception e) {
                log.error("onMessage error", e.getMessage(), e);
                int reconsumeTimes = msgs.get(0).getReconsumeTimes() + 1;

                if (reconsumeTimes >= consumerProperties.getConsumeRetryNum()) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } else {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
