package com.lmxdawn.dubboapi.req.trade;

import com.lmxdawn.dubboapi.req.ListPageDubboReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * USDT汇率
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class UsdtRateQueryDubboReq extends ListPageDubboReq {

    @ApiModelProperty(value = "状态（0: 全部，1：禁用，2：正常）")
    private Integer status;

}
