package com.honest.enterprise.core.annotation;

import java.lang.annotation.*;

/**
 * 策略注解
 * date:2022-07-17 13:25
 * author:fanjie
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AnnStrategy {
    String  value();

}
