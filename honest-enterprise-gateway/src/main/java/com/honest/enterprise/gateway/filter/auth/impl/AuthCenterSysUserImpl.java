package com.honest.enterprise.gateway.filter.auth.impl;


import com.honest.enterprise.core.annotation.AnnStrategy;
import com.honest.enterprise.core.http.HttpResult;
import com.honest.enterprise.feign.user.api.SysUserFeignService;
import com.honest.enterprise.gateway.filter.auth.AuthCenterAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 使用说明： 系统用户认证中心
 *
 * @author fanjie
 * @version 2.0.0
 * @createTime 2021年03月15日 14:27:00
 */
@Service
@AnnStrategy(value = AuthCenterTypeConst.SYS_USER)
public class AuthCenterSysUserImpl extends AuthCenterAbstract {
    @Autowired
    private SysUserFeignService sysUserFeignService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return super.filter(exchange, chain);
    }

    @Override
    protected HttpResult<Boolean> isLogin(String gdtoken) {
        return sysUserFeignService.isLogin(gdtoken);
    }

    @Override
    protected HttpResult<Boolean> hasPermission(String getoken, String path) {
        return sysUserFeignService.hasPermission(getoken, path);
    }

    @Override
    protected HttpResult<Integer> getUserIdByGeToken(String getoken) {
        return sysUserFeignService.getUserIdByGeToken(getoken);
    }
}
