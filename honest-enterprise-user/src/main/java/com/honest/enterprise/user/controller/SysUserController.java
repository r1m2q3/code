package com.honest.enterprise.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.honest.enterprise.core.http.HttpResult;
import com.honest.enterprise.user.dto.req.SysUserLoginReq;
import com.honest.enterprise.user.dto.resp.SysUserLoginResp;
import com.honest.enterprise.user.module.po.SysUser;
import com.honest.enterprise.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表服务控制器
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RestController
@RequestMapping(path = "/sysUser",produces = "application/json; charset=utf-8")
@Api(tags = "用户表服务控制器")
@RefreshScope
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/getUserInfoById")
    public SysUser getUserInfoById(Long id){
        return sysUserService.getById(id);
    }

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation("用户登录")
    public HttpResult<SysUserLoginResp> login(@RequestBody SysUserLoginReq request) {
        SysUserLoginResp sysUserLoginResp = sysUserService.login(request);
        if (sysUserLoginResp == null) {
            return HttpResult.LOGIN_ERROR;
        }
        return HttpResult.LOGIN_SUCCESS.setNewData(sysUserLoginResp);
    }


    @PostMapping("/isLogin")
    @ResponseBody
    @ApiOperation("是否登录")
    public HttpResult<Boolean> isLogin(@RequestBody String getoken) {
        Boolean ret = sysUserService.isLogin(getoken);
        return HttpResult.LOGIN_SUCCESS.setNewData(ret);
    }

    @GetMapping("/getUserIdByGeToken")
    @ResponseBody
    @ApiOperation("通过getoken获取用户id")
    public HttpResult<Integer> getUserIdByGeToken(@RequestParam String getoken) {
        System.out.println("-----gdtoken-----" + getoken);
        Object obj = StpUtil.getLoginIdByToken(getoken);
        System.out.println("-----obj-----" + obj);
        Integer userId = null;
        if (obj != null) {
            String objStr = (String) obj;
            userId = Integer.parseInt(objStr.split("_")[0]);
        }
        System.out.println("-----userId-----" + userId);
        return HttpResult.SUCCESS.setNewData(userId);
    }

    @GetMapping("/hasPermission")
    @ResponseBody
    public HttpResult<Boolean> hasPermission(@RequestParam String getoken, @RequestParam String permission) {
        boolean ret = sysUserService.hasPermission(getoken, permission);
        return HttpResult.SUCCESS.setNewData(ret);
    }


}