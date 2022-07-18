package com.honest.enterprise.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 使用说明：
 *
 * @author fanjie
 * @version 2.0.0
 * @createTime 2022年07月15日 14:27:00
 */

@SpringBootApplication
@EnableDiscoveryClient
//@EnableHystrix
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
