package com.honest.enterprise.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honest.enterprise.user.dao.SysDeptMapper;
import com.honest.enterprise.user.module.po.SysDept;
import lombok.extern.slf4j.Slf4j;
import com.honest.enterprise.user.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门表服务接口实现
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper , SysDept> implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;


    @Override
    public void test() {

    }
}