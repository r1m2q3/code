package com.honest.enterprise.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.honest.enterprise.user.module.po.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系(sys_role_menu)数据Mapper
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}
