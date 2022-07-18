package com.honest.enterprise.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.honest.enterprise.user.dto.req.SysUserLoginReq;
import com.honest.enterprise.user.dto.resp.SysUserLoginResp;
import com.honest.enterprise.user.module.po.SysUser;

/**
 * 用户表服务接口
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
public interface SysUserService  extends IService<SysUser> {
    /**
     * 用户登录
     * @param loginInfo
     * @return
     */
    SysUserLoginResp login(SysUserLoginReq loginInfo);

    /**
     * 是否登录
     * @param getoken
     * @return
     */
    boolean isLogin(String getoken);

    /**
     * 是否有权限
     * @param getoken
     * @param permission
     * @return
     */
    boolean hasPermission(String getoken, String permission);
}
