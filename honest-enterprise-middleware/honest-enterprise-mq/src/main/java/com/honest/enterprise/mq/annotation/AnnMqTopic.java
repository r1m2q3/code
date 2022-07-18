package com.honest.enterprise.mq.annotation;

import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.lang.annotation.*;

/**
 * mq topic注解
 * @date:2021-12-23 13:25
 * @author:fanjie
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AnnMqTopic {
    String  topic();
    String  tag();
    MessageModel messageModel() default MessageModel.CLUSTERING;
}
