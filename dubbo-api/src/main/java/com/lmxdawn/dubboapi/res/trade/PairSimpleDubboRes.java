package com.lmxdawn.dubboapi.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class PairSimpleDubboRes implements Serializable {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "手续费率")
    private Double fee;

    @ApiModelProperty(value = "手续费精度")
    private Integer feePrecision;

    @ApiModelProperty(value = "交易额精度")
    private Integer tradeTotalPrecision;

    @ApiModelProperty(value = "价格精度")
    private Integer tradePricePrecision;

    @ApiModelProperty(value = "买入量精度")
    private Integer tradeAmountPrecision;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;

}
