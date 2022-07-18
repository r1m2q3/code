package com.honest.enterprise.user.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    //@Override
    public void addInterceptors1(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler)->{
            // 根据路由划分模块，不同模块不同鉴权
        /*    SaRouterUtil.match("/sysUser/**", () -> StpUtil.checkPermission("user"));
            SaRouterUtil.match("/admin/**", () -> StpUtil.checkPermission("admin"));
            SaRouterUtil.match("/goods/**", () -> StpUtil.checkPermission("goods"));
            SaRouterUtil.match("/orders/**", () -> StpUtil.checkPermission("orders"));
            SaRouterUtil.match("/notice/**", () -> StpUtil.checkPermission("notice"));
            SaRouterUtil.match("/comment/**", () -> StpUtil.checkPermission("comment"));*/
        })).excludePathPatterns("/sysUser/login").addPathPatterns("/sysUser/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录拦截器，并排除登录接口地址
        registry.addInterceptor(new SaRouteInterceptor()).addPathPatterns("/**").excludePathPatterns("/sysUser/login");
    }
}
