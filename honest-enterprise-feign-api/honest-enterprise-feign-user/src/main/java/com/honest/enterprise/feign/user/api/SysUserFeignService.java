package com.honest.enterprise.feign.user.api;

import com.honest.enterprise.core.http.HttpResult;
import com.honest.enterprise.user.dto.req.SysUserLoginReq;
import com.honest.enterprise.user.dto.resp.SysUserLoginResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(path = "sysUser")
public interface SysUserFeignService {
    @PostMapping("/login")
    HttpResult<SysUserLoginResp> login(SysUserLoginReq request);

    @PostMapping("/isLogin")
    HttpResult<Boolean> isLogin(String getoken);

    @GetMapping("/hasPermission")
    HttpResult<Boolean> hasPermission(@RequestParam String getoken, @RequestParam String permission);

    /**
     * 通过dtUserId获取用户信息
     * @param getoken
     * @return
     */
    @GetMapping("/getUserIdByGeToken")
    HttpResult<Integer> getUserIdByGeToken(@RequestParam String getoken);

}
