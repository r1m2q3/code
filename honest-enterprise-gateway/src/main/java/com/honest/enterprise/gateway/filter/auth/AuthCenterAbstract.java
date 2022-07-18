package com.honest.enterprise.gateway.filter.auth;

import cn.hutool.core.util.StrUtil;
import com.honest.enterprise.core.auth.AuthConstant;
import com.honest.enterprise.core.http.HttpResult;
import com.honest.enterprise.gateway.config.FilterProperties;
import com.honest.enterprise.gateway.utils.FilterUtils;
import com.honest.enterprise.gateway.utils.HeaderNameConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 使用说明： 认证中心过滤抽象类
 *
 * @author fanjie
 * @version 2.0.0
 * @createTime 2021年03月23日 14:27:00
 */
public abstract class AuthCenterAbstract implements AuthCenterHandler {
    @Autowired
    private FilterProperties filterProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        //1.静态资源访问,接口通过判断url中是否含有指定的字符来判断是否放行,在这里设置url的白名单
        if (StrUtil.containsAnyIgnoreCase(path, filterProperties.getAllowPaths().toArray(new String[0]))) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        List<String> listToken = exchange.getRequest().getHeaders().get(HeaderNameConstant.TOKEN_NAME);
        if (listToken == null) {
            Mono<Void> filterError = FilterUtils.filterError(response, HttpResult.INVALID_TOKEN);
            if (filterError != null) {
                return filterError;
            }
        }
        String token = listToken.get(0);
        if (token == null || token.isEmpty()) {
            Mono<Void> filterError = FilterUtils.filterError(response, HttpResult.INVALID_TOKEN);
            if (filterError != null) {
                return filterError;
            }
        } else {
            HttpResult<Boolean> res = this.isLogin(token);
            if (!res.getData()) {
                Mono<Void> filterError = FilterUtils.filterError(response, HttpResult.LOGIN_ERROR);
                if (filterError != null) {
                    return filterError;
                }
            } else {
                HttpResult<Boolean> resPer = this.hasPermission(token, path);
                if (!resPer.getData()) {
                    Mono<Void> filterError = FilterUtils.filterError(response, HttpResult.ACCESS_LIMITED);
                    if (filterError != null) {
                        return filterError;
                    }
                }
                HttpResult<Integer> user = this.getUserIdByGeToken(token);
                if (Objects.equals(200, res.getRetCode())) {
                    //获取用户Id
                    exchange.getRequest().mutate().header(AuthConstant.CURRENT_USER_HEADER_ID, user.getData().toString()).build();
                }
            }
        }
        return null;
    }

    /**
     * 是否登录虚方法
     *
     * @param getoken
     * @return
     */
    protected abstract HttpResult<Boolean> isLogin(String getoken);

    /**
     * 是否有权限虚方法
     *
     * @param getoken
     * @param path
     * @return
     */
    protected abstract HttpResult<Boolean> hasPermission(String getoken, String path);

    /**
     * 通过token获取用户Id
     *
     * @param getoken
     * @return
     */
    protected abstract HttpResult<Integer> getUserIdByGeToken(String getoken);

}
