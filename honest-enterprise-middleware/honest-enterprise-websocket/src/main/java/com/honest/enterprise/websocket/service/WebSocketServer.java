package com.honest.enterprise.websocket.service;



import com.honest.enterprise.websocket.dto.WsNoticeDTO;

import javax.websocket.Session;

/**
 * WebSocket Server
 * @date 2022-05-26 15:09
 * @author fanjie
 */
public interface WebSocketServer {
    /**
     * 开启ws websocket
     * @param session
     * @param hetoken
     * @param wsMsgType
     */
    void onOpen(Session session, String hetoken, String wsMsgType);

    /**
     * 关闭ws
     */
    void onClose();

    /**
     * 消息接受
     * @param message
     * @param session
     */
    void onMessage(String message, Session session);

    /**
     * 错误
     * @param session
     * @param error
     */
    void onError(Session session, Throwable error);

    /**
     * 发送消息
     * @param message
     */
    void sendMessage(String message);

    /**
     * 发送消息
     * @param wsNotice
     * @param hetoken
     * @param wsMsgType
     */
    void sendInfo(WsNoticeDTO wsNotice, String hetoken, String wsMsgType);
}
