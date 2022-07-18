package com.honest.enterprise.core.model.interfaces;

/**
 * @Description: 自定义状态码接口
 * @Author: fanjie
 * @Date: 2022-07-17 14:03:10
 */
public interface BaseStatus {
    /**
     * 获取状态码
     * @return
     */
    int getCode();

    /**
     * 获取描述
     * @return
     */
    String getMsg();
}
