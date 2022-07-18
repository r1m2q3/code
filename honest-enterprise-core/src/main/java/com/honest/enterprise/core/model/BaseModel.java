package com.honest.enterprise.core.model;


import com.honest.enterprise.core.assembler.Copier;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段Model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基础字段")
public class BaseModel implements Serializable, Copier {

    /**
     * 创建人Id
     */
    @ApiModelProperty(value = "创建人Id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDt;

    /**
     * 修改人Id
     */
    @ApiModelProperty(value = "修改人Id")
    private Integer updateUserId;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateDt;

    /**
     * 删除状态 0：未删除  1：已删除
     */
    @ApiModelProperty(value = "删除状态 0：未删除  1：已删除")
    private Integer isDeleted;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;

}
