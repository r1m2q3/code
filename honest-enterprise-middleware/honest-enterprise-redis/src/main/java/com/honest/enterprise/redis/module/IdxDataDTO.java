package com.honest.enterprise.redis.module;

import lombok.Data;

/**
 * @author fanjie
 */
@Data
public class IdxDataDTO<T> {
    /**
     * 索引ID
     */
    private String id;
    /**
     * 索引数据
     */
    private T data;
}
