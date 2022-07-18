package com.honest.enterprise.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页Model
 */
@Data
@ApiModel("分页实体")
public class PageModel implements Serializable {
    @ApiModelProperty(value = "PageSize页面大小")
    private Integer pageSize;
    @ApiModelProperty(value = "PageNum页码")
    private Integer pageNum;
    @ApiModelProperty("排序字段")
    private String sortField;
    @ApiModelProperty("排序类型")
    private String sortType;
}
