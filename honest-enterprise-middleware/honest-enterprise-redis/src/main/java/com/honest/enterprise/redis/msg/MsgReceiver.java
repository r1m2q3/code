package com.honest.enterprise.redis.msg;

import cn.hutool.json.JSONUtil;
import com.honest.enterprise.core.model.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author fanjie
 * @date 2021-09-15
 * 消息接受
 */
//@Service
@Slf4j
public abstract class MsgReceiver implements MessageListener {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        //获取频道
        String channel=new String(message.getChannel());
        if(getTopic().equals(channel)) {
            String jsonMsg=new String(message.getBody());
            if(StringUtils.isEmpty(jsonMsg)){
                log.error("MsgReceiver msg is empty!");
                return;
            }
            jsonMsg = jsonMsg.replaceAll("\\\\\\\"","\\\"");
            jsonMsg = jsonMsg.replaceAll("\\\\\\\\\"","\\\\\"");
            jsonMsg = jsonMsg.substring(1);
            jsonMsg = jsonMsg.substring(0,jsonMsg.length() - 1);

            String key=channel+"_";
            if(jsonMsg.contains("{")&&jsonMsg.contains("}")){
                MsgDTO msg= JSONUtil.toBean(jsonMsg, MsgDTO.class);
                if(msg==null){
                    log.error("MsgReceiver msg format error!");
                    return;
                }
                key+=msg.getMsgId();
            }else{
                key+=jsonMsg;
            }

            //分布式锁
            RLock lock= redissonClient.getLock(key);
            //如果获取了锁
            if(lock.tryLock()) {
                handleMessage(jsonMsg);
                lock.unlock();
            }
        }
    }

    /**
     * 消息处理
     * @param jsonMsg
     * @return
     */
    protected abstract void handleMessage(String jsonMsg);

    /**
     * 获取订阅主题
     * @return
     */
    protected abstract String getTopic();
}
