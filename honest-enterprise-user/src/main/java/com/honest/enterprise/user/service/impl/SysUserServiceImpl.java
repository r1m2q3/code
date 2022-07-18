package com.honest.enterprise.user.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honest.enterprise.user.dao.SysMenuMapper;
import com.honest.enterprise.user.dao.SysUserMapper;
import com.honest.enterprise.user.dto.SysUserDTO;
import com.honest.enterprise.user.dto.req.SysUserLoginReq;
import com.honest.enterprise.user.dto.resp.SysUserLoginResp;
import com.honest.enterprise.user.module.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import com.honest.enterprise.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户表服务接口实现
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private  SysUserMapper sysUserMapper;

    @Override
    public SysUserLoginResp login(SysUserLoginReq loginInfo) {
        SysUserDTO sysUser=this.getUserByDomainAndAcc(loginInfo.getDomain(),loginInfo.getUserName());

        //判断用户是否存在，以及密码是否正确
        if (sysUser != null && sysUser.getPassword().equals(loginInfo.getPassword())) {
            //StpUtil.setLoginId(sysUser.getId());
            StpUtil.login(sysUser.getId() + "_" + loginInfo.getDomain());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return SysUserLoginResp.builder()
                    .name(sysUser.getTrueName())
                    .userId(sysUser.getId().longValue())
                    .deptName("诚信企业网").deptId(1L)
                    .token(StpUtil.getTokenValue()).build();
        }
        return null;

    }

    @Override
    public boolean isLogin(String getoken) {
        // 获取指定token对应的登录id，如果未登录，则返回 null
        Object obj=StpUtil.getLoginIdByToken(getoken);
        StpUtil.login(obj.toString());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        if (tokenInfo != null) {
            // 先检查是否已过期
            StpUtil.checkActivityTimeout();
            // 检查通过后继续续签
            StpUtil.updateLastActivityToNow();
            if(tokenInfo.isLogin){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(String getoken, String permission) {
        // 当前账号是否含有指定权限, 返回true或false
        Object obj = StpUtil.getLoginIdByToken(getoken);

        if (obj != null) {
            //return StpUtil.hasPermission(obj, permission);//的权限放开
            return true;
        } else {
            return false;
        }
    }

    private SysUserDTO getUserByDomainAndAcc(Integer domain,String account){
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.eq("domain",domain);
        query.eq("account",account);
        query.eq("deleted",0);
        query.eq("status",1);

        SysUser data=sysUserMapper.selectOne(query);
        if (Objects.isNull(data)) {
            return null;
        }
        return data.copy(SysUserDTO.class);

    }
}