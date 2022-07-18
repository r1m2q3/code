package com.honest.enterprise.mq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者配置类
 * @author fanjie
 * @date 2021-12-22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq.consumer")
public class MqConsumerProperties {
    /**
     * 组名称
     */
    private String groupName;
    /**
     * mq 地址
     */
    private String namesrvAddr;
    /**
     * 消费重试次数
     */
    private Integer consumeRetryNum;

    /**
     * 消费者线程最小数
     */
    private Integer consumeThreadMin;
    /**
     * 消费者线程最大数
     */
    private Integer consumeThreadMax;
    /**
     * 批量消费,每次拉取的条数
     */
    private Integer consumeMessageBatchMaxSize;
}
