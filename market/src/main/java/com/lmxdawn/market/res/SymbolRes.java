package com.lmxdawn.market.res;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "委托订单")
@Data
public class SymbolRes {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "交易币种")
    private CoinSimpleDubboRes tradeCoin;

    @ApiModelProperty(value = "计价币种")
    private CoinSimpleDubboRes coin;

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
}
