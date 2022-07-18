package com.honest.enterprise.config.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Description: 自定义Jackson ObjectMapper 解决long类型精度丢失问题
 * @Author: fanjie
 * @Date: 2022-07-17 17:57:52
 */
@Configuration
public class CustomJacksonObjectMapper {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
//            jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
//            jacksonObjectMapperBuilder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
        };
    }

}

