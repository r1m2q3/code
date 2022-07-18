package com.honest.enterprise.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honest.enterprise.core.http.HttpResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * filter工具类
 * date:2021-03-23 10:42:00
 * author:fanjie
 */
public class FilterUtils {
    public static Mono<Void> filterError(ServerHttpResponse response, HttpResult httpStatus){
        return filterError(response,httpStatus.getRetCode(),httpStatus.getRetMsg());
    }
    public static Mono<Void> filterError(ServerHttpResponse response, int code,String message){
        HttpResult<String> ret=new HttpResult<>();
        ret.setRetCode(code);
        ret.setRetMsg(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] bytes = mapper.writeValueAsBytes(ret);
            // 输出错误信息到页面
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            if(response.getHeaders()!=null) {
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            }
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

