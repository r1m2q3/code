package com.honest.enterprise.user;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * (exclude= {DataSourceAutoConfiguration.class}) : 不配置数据源
 */
/*
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableCaching
*/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.honest.enterprise.user.dao")
@EnableAsync
@EnableScheduling

@EnableAspectJAutoProxy(exposeProxy = true)

public class UserApp {

  public static void main(String[] args) {
    SpringApplication.run(UserApp.class, args);
    System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
  }

}
