package com.honest.enterprise.gateway.filter;


import com.honest.enterprise.core.exception.GeCenteringException;
import com.honest.enterprise.core.http.HttpResult;
import com.honest.enterprise.core.strategy.StrategyContext;
import com.honest.enterprise.gateway.filter.auth.AuthCenterHandler;
import com.honest.enterprise.gateway.filter.auth.impl.AuthCenterTypeConst;
import com.honest.enterprise.gateway.utils.FilterUtils;
import com.honest.enterprise.gateway.utils.HeaderNameConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 使用说明： 过滤器
 *
 * @author fanjie
 * @version 2.0.0
 * @createTime 2022年07月16日 11:11
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private StrategyContext strategyContext;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        try {
            //1.获取认证类型，如果没认证类型，则返回错误
            /*List<String> listAuthType= exchange.getRequest().getHeaders().get(HeaderNameConstant.AUTH_TYPE_NAME);
            if(listAuthType==null){
                Mono<Void> filterError= FilterUtils.filterError(response,HttpResult.INVALID_AUTH_CENTER);
                if(filterError!=null){
                    return filterError;
                }
            }*/
            String authType= AuthCenterTypeConst.SYS_USER;//listAuthType.get(0);
            //2.通过不同的认证类型走不同的，认证逻辑(策略模式)
            AuthCenterHandler auth=strategyContext.getStrategyImpl(AuthCenterHandler.class,authType);
            Mono<Void>  filter= auth.filter(exchange,chain);
            return filter==null?chain.filter(exchange):filter;
        }catch (GeCenteringException e){
            log.error("filter.error",e.getMessage(),e);
            return FilterUtils.filterError(response,e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("filter.error",e.getMessage(),e);
            return FilterUtils.filterError(response,HttpResult.UNKOWN_ERROR);
        }

    }

    //设置过滤器的执行顺序
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}