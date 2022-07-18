package com.honest.enterprise.feign.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * 1.扫所有AnnFeignFallBack
 * 2.后置置入抛出异常
 * 3.拦截异常,抛出熔断错误编码httpresult
 */
//@Aspect
@Slf4j
public class AopFeignFallBack {


    @Around("execution(* com.honest.enterprise..*.feign.api.fallback..*.*(..)) ")
    public Object around(ProceedingJoinPoint pjp){
        try {
            Object[] args = pjp.getArgs();
            Throwable throwable= (Throwable) args[0];
            String msg = throwable == null ? "" : throwable.getMessage();
            if (!StringUtils.isEmpty(msg)) {
                log.error(msg);
            }
            log.info("AopFeignFallBack is 熔断！");
            return throwable;
        }catch (Exception e){
            log.error("aop feign 异常"+e.getMessage());
        }
        return null;
    }

}
