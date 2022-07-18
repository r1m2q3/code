package com.honest.enterprise.feign.core.config;


import com.netflix.hystrix.HystrixCommand;
import feign.Feign;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * feign 默认开启熔断配置类
 * author:fanjie
 * date:2021-03-23
 */
@Configuration
public class FeignClientsConfiguration {
    @Configuration
    @ConditionalOnClass({ HystrixCommand.class, HystrixFeign.class})
    protected static class HystrixFeignConfiguration {
        @Bean
        @Scope("prototype")
        @ConditionalOnMissingBean
        @ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = true)
        public Feign.Builder feignHystrixBuilder() {
            return HystrixFeign.builder();
        }
    }
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}