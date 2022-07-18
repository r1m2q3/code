package com.honest.enterprise.user.module.po;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import com.honest.enterprise.core.assembler.Copier;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户表(sys_user)实体类
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> implements Serializable , Copier {
    private static final long serialVersionUID = 1L;

    /**
     * 注解ID
     */
    @TableId
	private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 用户领域
1:买家用户
2:卖家用户
3:系统管理平台用户
4:平台投资人股东用户
     */
    private Integer domain;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 性别 0：未知  1：男 2：女 default:1
     */
    private Integer gender;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户手机号
     */
    private String userMobile;
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
    @TableField(update = "now()")
	private Date updateTime;
    /**
     * 是否删除0：否  1：是 default:0
     */
    private Integer deleted;

}