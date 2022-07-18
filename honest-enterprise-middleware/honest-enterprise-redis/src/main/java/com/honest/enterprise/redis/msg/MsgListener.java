package com.honest.enterprise.redis.msg;


import cn.hutool.core.util.ReflectUtil;
import com.honest.enterprise.core.utils.IocContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Map;

@Slf4j
@Configuration
public class MsgListener {
    @Autowired
    private IocContextUtils iocContextUtils;
    /**
     * 创建连接工厂
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //1.注册对象到bean
        Map<String,MsgReceiver> mapMsg=iocContextUtils.getBeansOfType(MsgReceiver.class);
        if(mapMsg==null||mapMsg.size()==0){
            return container;
        }
        for (Map.Entry<String, MsgReceiver> entry : mapMsg.entrySet()) {
            MessageListenerAdapter ada=new MessageListenerAdapter(entry.getValue(),"onMessage");
            iocContextUtils.registerBean(entry.getKey(),ada);
        }


        //2.注册消费者
        Map<String,MessageListenerAdapter> mapIoc=iocContextUtils.getBeansOfType(MessageListenerAdapter.class);
        if(mapIoc!=null){
            for (Map.Entry<String, MessageListenerAdapter> entry : mapIoc.entrySet()) {
                MessageListenerAdapter listenerAdapter1=(MessageListenerAdapter)entry.getValue().getDelegate();
                String topic=ReflectUtil.invoke(listenerAdapter1.getDelegate(),"getTopic").toString();
                container.addMessageListener(listenerAdapter1,new PatternTopic(topic));
            }
        }
        //接受消息的key
        return container;
    }
}
