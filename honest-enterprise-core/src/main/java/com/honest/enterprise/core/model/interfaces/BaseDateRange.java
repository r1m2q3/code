package com.honest.enterprise.core.model.interfaces;

/**
 * @Description:
 * @Author: fanjie
 * @Date: 2022-07-17 10:02:42
 */
public interface BaseDateRange {

    /**
     *  get start date
     * @return
     */
    String getStartDt();

    /**
     *  set start date
     * @param startDt
     */
    void setStartDt(String startDt);

    /**
     *  get end date
     * @return
     */
    String getEndDt();

    /**
     *  set end date
     * @param endDt
     */
    void setEndDt(String endDt);
}
