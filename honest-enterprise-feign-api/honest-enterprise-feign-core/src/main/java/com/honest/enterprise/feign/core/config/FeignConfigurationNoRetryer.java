package com.honest.enterprise.feign.core.config;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignConfigurationNoRetryer {

  /**
   * 自定义重试机制
   * 禁止重试
   * @return
   */
  @Bean
  public Retryer feignRetryer() {
    return Retryer.NEVER_RETRY;
  }


  @Bean
  public ErrorDecoder feignError() {
    return (key, response) -> {
      if (response.status() == 400) {
        log.error("请求xxx服务400参数错误,返回:{}", response.body());
      }
      if (response.status() == 409) {
        log.error("请求xxx服务409异常,返回:{}", response.body());
      }
      if (response.status() == 404) {
        log.error("请求xxx服务404异常,返回:{}", response.body());
      }
      // 其他异常交给Default去解码处理
      // 这里使用单例即可，Default不用每次都去new
      return new ErrorDecoder.Default().decode(key, response);
    };
  }
}
