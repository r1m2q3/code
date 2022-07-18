package com.honest.enterprise.user.dto;

import com.honest.enterprise.core.assembler.Copier;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表(sys_menu)实体类
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SysMenuDTO implements Serializable, Copier {
    private static final long serialVersionUID = 1L;

    /**
     * menuId
     */
	private Long menuId;
    /**
     * 用户领域
1:买家用户
2:卖家用户
3:系统管理平台用户
4:平台投资人股东用户
     */
    private Integer domain;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentMenuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单URL
     */
    private String menuUrl;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String menuPerms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer menuType;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 状态 0：停用 1：启用 default :1
     */
    private Integer status;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 是否删除0：否  1：是 default:0
     */
    private Integer deleted;

}