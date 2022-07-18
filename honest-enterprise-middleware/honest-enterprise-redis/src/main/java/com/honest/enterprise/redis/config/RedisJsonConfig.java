package com.honest.enterprise.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.providers.PooledConnectionProvider;

/**
 * redis json 配置类
 * @author fanjie
 * @Date 2022-4-25
 */
@Configuration
@ConditionalOnProperty(value = "spring.redis.database",havingValue="0" )
public class RedisJsonConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Bean
    public UnifiedJedis unifiedJedis() {
        HostAndPort config = new HostAndPort(host, Integer.valueOf(port));
        PooledConnectionProvider provider = new PooledConnectionProvider(config);
        UnifiedJedis redisJsonClient = new UnifiedJedis(provider);
        redisJsonClient.sendCommand(Protocol.Command.AUTH, password);
        return redisJsonClient;

    }
}
