package com.honest.enterprise.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honest.enterprise.user.dao.SysRoleMapper;
import com.honest.enterprise.user.module.po.SysRole;
import lombok.extern.slf4j.Slf4j;
import com.honest.enterprise.user.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表服务接口实现
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private SysRoleMapper sysRoleMapper;

    @Override
    public void test() {

    }
}