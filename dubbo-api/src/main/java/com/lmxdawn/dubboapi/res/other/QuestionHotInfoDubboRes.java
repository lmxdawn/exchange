package com.lmxdawn.dubboapi.res.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class QuestionHotInfoDubboRes implements Serializable {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

}
