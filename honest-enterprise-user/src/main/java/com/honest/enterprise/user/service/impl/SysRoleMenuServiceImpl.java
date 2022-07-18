package com.honest.enterprise.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honest.enterprise.user.dao.SysRoleMenuMapper;
import com.honest.enterprise.user.module.po.SysRoleMenu;
import lombok.extern.slf4j.Slf4j;
import com.honest.enterprise.user.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色与菜单对应关系服务接口实现
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private  SysRoleMenuMapper SysRoleMenuMapper;

    @Override
    public void test() {

    }
}