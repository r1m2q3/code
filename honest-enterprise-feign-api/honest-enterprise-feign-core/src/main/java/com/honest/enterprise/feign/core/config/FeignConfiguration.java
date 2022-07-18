package com.honest.enterprise.feign.core.config;

import cn.hutool.json.JSONUtil;

import com.honest.enterprise.core.http.HttpResult;
import feign.Response;
import feign.Retryer;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author fanjie
 * @summary fegin 客户端的自定义配置
 * date:2021-03-26
 */
@Slf4j
public class FeignConfiguration {
    /**
     * 自定义重试机制
     *
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        //最大请求次数为5，初始间隔时间为100ms，下次间隔时间1.5倍递增，重试间最大间隔时间为1s，
        return new Retryer.Default();
    }

    @Bean
    public ErrorDecoder feignError() {
        return new ExceptionErrorDecoder();
    }

    public class ExceptionErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {

            String body = null;
            if (response.body() != null) {
                try {
                    body = Util.toString(response.body().asReader(Charset.defaultCharset()));
                } catch (IOException e) {
                    log.error("feign.IOException", e);
                }
                HttpResult result = JSONUtil.toBean(body, HttpResult.class);
                if (response.status() == 400) {
                    log.error("请求xxx服务400参数错误,返回:{}", body);
                }
                if (response.status() == 409) {
                    log.error("请求xxx服务409异常,返回:{}", body);
                }
                if (response.status() == 404) {
                    log.error("请求xxx服务404异常,返回:{}", body);
                }
                return new RuntimeException(result.getData().toString());
            }
            // 其他异常交给Default去解码处理
            // 这里使用单例即可，Default不用每次都去new
            return new Default().decode(methodKey, response);

        }
    }
}
