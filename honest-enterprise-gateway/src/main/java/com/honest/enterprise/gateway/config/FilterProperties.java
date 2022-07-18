package com.honest.enterprise.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 白名单配置
 * @author fanjie
 * @createTime 2022年07月15日 14:27:00
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "he-gateway.filter")
public class FilterProperties {
    private List<String> allowPaths;
}
