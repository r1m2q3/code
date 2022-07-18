package com.honest.enterprise.core.annotation;

import java.lang.annotation.*;

/**
 * @author fanjie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnoPermission {

    /**
     * 是否菜单 0：否 1：是
     *
     * @return
     */
    int isMenu() default 0;
}
