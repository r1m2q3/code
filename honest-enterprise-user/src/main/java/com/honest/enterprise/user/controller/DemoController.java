package com.honest.enterprise.user.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "demo服务控制器")
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {
    @GetMapping("/hello")
    public String hello(){
        return "hello!!";
    }
}
