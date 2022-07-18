package com.honest.enterprise.feign.core.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Description:
 * @Author: fanjie
 * @Date: 2021-04-15 18:36:09
 */
@Configuration
@ComponentScan(value = {"com.honest.enterprise.**.feign.**.fallback"})
@EnableFeignClients(basePackages = "com.honest.enterprise.**.feign")
public class FeignScanEnable {

}
