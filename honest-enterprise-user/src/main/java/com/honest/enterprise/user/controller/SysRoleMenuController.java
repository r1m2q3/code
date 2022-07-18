package com.honest.enterprise.user.controller;

import com.honest.enterprise.user.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色与菜单对应关系服务控制器
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RestController
@RequestMapping(path = "/sysRoleMenu",produces = "application/json; charset=utf-8")
@Api(tags = "角色与菜单对应关系服务控制器")
@RefreshScope
public class SysRoleMenuController {
    @Autowired
    private  SysRoleMenuService sysRoleMenuService;

}