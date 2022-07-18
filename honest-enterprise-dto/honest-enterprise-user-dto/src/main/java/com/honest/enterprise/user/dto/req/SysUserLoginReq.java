package com.honest.enterprise.user.dto.req;

import com.honest.enterprise.core.assembler.Copier;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanjie
 */
@Data
@ApiModel("用户登录请求体")
public class SysUserLoginReq implements Serializable, Copier {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("domain")
    private Integer domain;

    @ApiModelProperty("验证码")
    private String verificationCode;
}