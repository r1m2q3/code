package com.honest.enterprise.mq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 生产者配置类
 * @author fanjie
 * @date 2021-12-22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MqProducerProperties {

    /**
     * namesrvAddr
     */
    private String namesrvAddr;
    /**
     * group
     */
    private String groupName;
}
