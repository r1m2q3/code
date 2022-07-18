package com.honest.enterprise.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.honest.enterprise.user.module.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(sys_user)数据Mapper
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
