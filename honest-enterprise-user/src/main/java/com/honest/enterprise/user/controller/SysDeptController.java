package com.honest.enterprise.user.controller;

import com.honest.enterprise.user.service.SysDeptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 部门表服务控制器
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RestController
@RequestMapping(path = "/sysDept",produces = "application/json; charset=utf-8")
@Api(tags = "部门表服务控制器")
@RefreshScope
public class SysDeptController {
    @Autowired
    private  SysDeptService sysDeptService;

}