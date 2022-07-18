package com.honest.enterprise.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honest.enterprise.user.dao.SysRoleDeptMapper;
import com.honest.enterprise.user.module.po.SysRoleDept;
import lombok.extern.slf4j.Slf4j;
import com.honest.enterprise.user.service.SysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色与部门对应关系服务接口实现
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public void test() {

    }
}