package com.lmxdawn.trade.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "USDT汇率")
@Data
public class UsdtRateRes {

    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "符号")
    private String symbol;

    @ApiModelProperty(value = "USDT汇率")
    private Double price;

    @ApiModelProperty(value = "精度")
    private Integer precision;
}
