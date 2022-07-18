package com.honest.enterprise.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {
  @Bean
  public DefaultRedisScript<Boolean> redisScript() {
    DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
    redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/Test.lua")));
    redisScript.setResultType(Boolean.class);
    return redisScript;
  }

  @Bean
  public DefaultRedisScript<Long> limitRate() {
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
    redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/rateLimit.lua")));
    redisScript.setResultType(Long.class);
    return redisScript;
  }

}
