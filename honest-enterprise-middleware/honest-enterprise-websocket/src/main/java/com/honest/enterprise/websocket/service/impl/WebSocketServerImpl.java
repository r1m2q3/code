package com.honest.enterprise.websocket.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.honest.enterprise.websocket.dto.WsNoticeDTO;
import com.honest.enterprise.websocket.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket的处理类。
 * 作用相当于HTTP请求
 * 中的controller
 *
 * @author fanjie
 */
@Component
@Slf4j
@ServerEndpoint("/ws-service/{hetoken}/{wsMsgType}")//前端通过此 URI 和后端交互，建立连接
public class WebSocketServerImpl implements WebSocketServer, ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (WebSocketServerImpl.applicationContext == null) {
            WebSocketServerImpl.applicationContext = applicationContext;
        }
    }

    /**
     * 记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServerImpl> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收hetoken
     */
    private String hetoken = "";
    /**
     * 接收wsMsgType
     */
    private String wsMsgType = "";


    /**
     * 连接建立成
     * 功调用的方法
     */
    @Override
    @OnOpen//建立连接的注解，前端触发上面 URI 时会进入此注解标注的方法
    public void onOpen(Session session, @PathParam("hetoken") String hetoken, @PathParam("wsMsgType") String wsMsgType) {
        this.session = session;
        this.hetoken = hetoken;
        this.wsMsgType = wsMsgType;
        String key = buildSessionKey(this.hetoken, this.wsMsgType);
        if (webSocketMap.containsKey(key)) {
            webSocketMap.remove(key);
            //加入set中
            webSocketMap.put(key, this);
        } else {
            //加入set中
            webSocketMap.put(key, this);
            //在线数加1
            onlineCount.incrementAndGet();
        }
        log.info("用户连接:" + key + ",当前在线人数为:" + onlineCount.get());
        sendMessage("连接成功");
    }

    /**
     * 连接关闭
     * 调用的方法
     */
    @Override
    @OnClose//关闭连接，销毁 session
    public void onClose() {
        String key = buildSessionKey(this.hetoken, this.wsMsgType);
        if (webSocketMap.containsKey(key)) {
            webSocketMap.remove(key);
            //在线数减一
            onlineCount.decrementAndGet();
        }
        log.info("用户退出:" + key + ",当前在线人数为:" + onlineCount.get());
    }

    /**
     * 收到客户端消
     * 息后调用的方法
     *
     * @param message 客户端发送过来的消息
     **/
    @Override
    @OnMessage// 收到前端传来的消息后执行的方法
    public void onMessage(String message, Session session) {
        String key = buildSessionKey(this.hetoken, this.wsMsgType);
        log.info("用户消息:" + key + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSONUtil.parseObj(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", key);

                String toUserId = jsonObject.getStr("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(message);
                } else {
                    //否则不在这个服务器上，发送到mysql或者redis
                    log.error("请求的userId:" + toUserId + "不在该服务器上");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param session
     * @param error
     */
    @Override
    @OnError
    public void onError(Session session, Throwable error) {
        String key = buildSessionKey(this.hetoken, this.wsMsgType);
        log.error("用户错误:" + key + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务
     * 器主动推送
     */
    @Override
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送自定
     * 义消息
     **/
    @Override
    public void sendInfo(WsNoticeDTO wsNotice, String hetoken, String wsMsgType) {
        if (wsNotice == null) {
            return;
        }
        String message = JSONUtil.toJsonStr(wsNotice);
        String key = buildSessionKey(hetoken, wsMsgType);

        if (StringUtils.isNotBlank(key) && webSocketMap.containsKey(key)) {
            log.info("发送消息到:" + key + "，报文:" + message);
            webSocketMap.get(key).sendMessage(message);
        }
    }

    /**
     * 构建会话key
     *
     * @param hetoken
     * @param wsMsgType
     * @return
     */
    private String buildSessionKey(String hetoken, String wsMsgType) {
        String key = hetoken + "_" + wsMsgType;
        return key;
    }

}

