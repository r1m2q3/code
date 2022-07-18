package com.honest.enterprise.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honest.enterprise.user.dao.SysUserRoleMapper;
import com.honest.enterprise.user.module.po.SysUserRole;
import lombok.extern.slf4j.Slf4j;
import com.honest.enterprise.user.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户与角色对应关系服务接口实现
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void test() {

    }
}