package com.honest.enterprise.user.controller;

import com.honest.enterprise.user.service.SysUserRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户与角色对应关系服务控制器
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RestController
@RequestMapping(path = "/sysUserRole",produces = "application/json; charset=utf-8")
@Api(tags = "用户与角色对应关系服务控制器")
@RefreshScope
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

}