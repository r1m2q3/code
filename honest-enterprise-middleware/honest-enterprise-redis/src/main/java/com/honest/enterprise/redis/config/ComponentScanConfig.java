package com.honest.enterprise.redis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描包路径下的类到容器
 * author:fanjie
 * date:2021-03-25
 */
@Configuration
@ComponentScan(value = {"com.honest.enterprise.redis.utils"})
public class ComponentScanConfig {

}
