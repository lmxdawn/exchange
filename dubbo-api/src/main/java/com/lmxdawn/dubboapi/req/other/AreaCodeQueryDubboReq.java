package com.lmxdawn.dubboapi.req.other;

import com.lmxdawn.dubboapi.req.ListPageDubboReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 协议
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class AreaCodeQueryDubboReq extends ListPageDubboReq {

    @ApiModelProperty(value = "状态（-1: 全部，0：禁用，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "语言")
    private String lang;

}
