package com.honest.enterprise.feign.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc 拦截feign异常return
 */
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Target(ElementType.METHOD) // 作用到方法上
public @interface AnnFeignFallBack {

    String name() default "";

}
