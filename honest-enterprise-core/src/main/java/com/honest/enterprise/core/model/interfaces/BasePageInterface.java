package com.honest.enterprise.core.model.interfaces;

/**
 * @Description:
 * @Author: fanjie
 * @Date: 2022-07-17 10:11:15
 */
public interface BasePageInterface {

    /**
     * get Page num
     * @return
     */
    Integer getPageNum();

    /**
     * set Page num
     * @param pageNum
     */
    void setPageNum(Integer pageNum);

    /***
     * get Page size
     * @return
     */
    Integer getPageSize();

    /**
     * set Page size
     * @param pageSize
     */
    void setPageSize(Integer pageSize);
}
