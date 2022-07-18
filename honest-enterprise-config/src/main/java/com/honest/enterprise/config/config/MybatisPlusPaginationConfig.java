package com.honest.enterprise.config.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Description:
 * @Author: fanjie
 * @Date: 2022-07-17 18:14:08
 */
@Configuration
public class MybatisPlusPaginationConfig {
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();

        PaginationInnerInterceptor paginationInterceptor=new PaginationInnerInterceptor();
        //默认最大限制500，即一页最多查出500条数据 -1不限制
        paginationInterceptor.setMaxLimit(5000L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor);
        return mybatisPlusInterceptor;
    }
}