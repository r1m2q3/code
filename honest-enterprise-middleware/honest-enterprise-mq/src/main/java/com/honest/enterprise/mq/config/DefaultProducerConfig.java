package com.honest.enterprise.mq.config;


import com.honest.enterprise.mq.properties.MqProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author fanjie
 * @date 2021/12/22 2:04 下午
 */
@Slf4j
@Configuration
public class DefaultProducerConfig {

    @Autowired
    private MqProducerProperties propertiesProperties;

    /**
     * 创建普通消息发送者实例
     *
     * @return
     * @throws MQClientException
     */
    @Bean
    @Primary
    public DefaultMQProducer defaultProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(propertiesProperties.getGroupName());
        producer.setNamesrvAddr(propertiesProperties.getNamesrvAddr());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);

        if(propertiesProperties==null|| StringUtils.isEmpty(propertiesProperties.getNamesrvAddr())){
            log.info("not rocket mq config info");
            return producer;
        }

        producer.start();
        log.info("default producer 创建成功, {}, {}", propertiesProperties.getNamesrvAddr(), propertiesProperties.getGroupName());
        return producer;
    }
}
