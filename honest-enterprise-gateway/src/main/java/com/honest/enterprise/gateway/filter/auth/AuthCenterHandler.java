package com.honest.enterprise.gateway.filter.auth;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 使用说明： 认证中心过滤 接口
 *
 * @author fanjie
 * @version 2.0.0
 * @createTime 2021年03月15日 14:27:00
 */
public interface AuthCenterHandler {
    /**
     * 权限过滤
     * @param exchange
     * @param chain
     * @return
     */
    Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
}
