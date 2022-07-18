package com.honest.enterprise.user.dto.resp;

import com.honest.enterprise.core.assembler.Copier;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@ApiModel("用户登录相应体")
public class SysUserLoginResp implements Serializable, Copier {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("部门Id")
    private Long deptId;
}
