package com.honest.enterprise.user.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.dev33.satoken.stp.StpInterface;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成sa-token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    /**
     * 功能模块
     */
    //@Autowired
    //private SysFunctionAppService sysFunctionAppService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        List<String> list = new ArrayList<String>();
        //get userId and identityId

        /*
        String[] loginInfo = loginId.toString().split("_");
        List<SysFunctionDTO> sysFunctionDTOS = sysFunctionAppService.listByUserIdAndIdentityId(Integer.parseInt(loginInfo[0]), Integer.parseInt(loginInfo[1]));

        if (null != sysFunctionDTOS && sysFunctionDTOS.size() > 0) {
            List<String> pathList = sysFunctionDTOS.stream().map(SysFunctionDTO::getPath).collect(Collectors.toList());
            list.addAll(pathList);
        }

         */
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<String>();
       /* list.add("admin");
        list.add("super-admin");*/
        return list;
    }
}
