package com.lmxdawn.dubboapi.req.other;

import com.lmxdawn.dubboapi.req.ListPageDubboReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 热门问题
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionHotQueryDubboReq extends ListPageDubboReq {

    @ApiModelProperty(value = "状态（-1: 全部，0：禁用，1：正常）")
    private Integer status;

}
