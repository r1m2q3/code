package com.honest.enterprise.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanjie
 * @date 2021-11-17
 */
@Configuration
@ConditionalOnProperty(value = "spring.redis.database",havingValue="0" )
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);

        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});

        /** 集群
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress("redis://10.0.29.30:6379", "redis://10.0.29.95:6379")
                .addNodeAddress(node).setPassword(password);;
         */

        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }


}
