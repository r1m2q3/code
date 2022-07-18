package com.honest.enterprise.core.annotation;

import java.lang.annotation.*;

/**
 * @Description: AOP统一处理（分布式id、创建人、创建时间、修改人、修改时间、是否删除）
 * @params:
 * @Author: fanjie
 * @Date: 2022-07-17 9:26
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnoSetCommonAttr {
    /**
     *是否批量 批量的话id不需要
     */
    //boolean isBatch()  default false;
}
