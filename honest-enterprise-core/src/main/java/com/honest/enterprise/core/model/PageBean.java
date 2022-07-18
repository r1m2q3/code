package com.honest.enterprise.core.model;


import com.honest.enterprise.core.assembler.Copier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 自定义分页对象
 * @Author: fanjie
 * @Date: 2022-07-17 15:31:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> implements Serializable, Copier {

    private static final long serialVersionUID = -379999804551452224L;

    public PageBean(int pageNum, int pageSize, int pageCount, int total, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.total = total;
        this.records = records;
    }

    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 总数
     */
    private int total;

    /**
     * 最后更新数据的时间
     */
    private String updateTime;

    /**
     * 数据
     */
    private List<T> records;

    public static <T> PageBean<T> createPageBean(int pageNum, int pageSize,int pageCount, long total, List<T> records) {
        Integer size = Long.valueOf(total).intValue();
        PageBean<T> pageBean = new PageBean(pageNum, pageSize,pageCount, size, records);
        return pageBean;
    }

    public static <T> PageBean<T> createPageBean(int pageNum, int pageSize,Long pageCount, long total, List<T> records) {
        Integer size = Long.valueOf(total).intValue();
        PageBean<T> pageBean = new PageBean(pageNum, pageSize,pageCount.intValue(), size, records);
        return pageBean;
    }
    public static <T> PageBean<T> createPageBean(Long pageNum, Long pageSize, Long pageCount, long total, List<T> records) {
        Integer size = Long.valueOf(total).intValue();
        PageBean<T> pageBean = new PageBean(pageNum.intValue(), pageSize.intValue(),pageCount.intValue(), size, records);
        return pageBean;
    }

}
