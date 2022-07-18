package com.honest.enterprise.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 * @Description:
 * @Author: fanjie
 * @Date: 2022-05-26 14:36:09
 */
@Configuration
//@EnableWebSocket
public class WebSocketConfig {
  /*  @Bean
    public ServerEndpointExporter serverEndpoint() {
        return new ServerEndpointExporter();
    }*/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        return new ServerEndpointExporter();
    }
}
