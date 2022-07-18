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
 * 用户与角色对应关系(sys_user_role)实体类
 *
 * @author fanjie
 * @since 2022-07-17 16:01:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> implements Serializable, Copier {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
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