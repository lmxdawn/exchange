package com.lmxdawn.trade.res;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "委托订单")
@Data
public class PairRes {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "交易币种")
    private CoinSimpleDubboRes tradeCoin;

    @ApiModelProperty(value = "计价币种")
    private CoinSimpleDubboRes coin;

    @ApiModelProperty(value = "交易额精度")
    private Integer tradeTotalPrecision;

    @ApiModelProperty(value = "价格精度")
    private Integer tradePricePrecision;

    @ApiModelProperty(value = "买入量精度")
    private Integer tradeAmountPrecision;

    @ApiModelProperty(value = "最新价格")
    private Double price;

    @ApiModelProperty(value = "(24h)之前的价格")
    private Double price24;

    @ApiModelProperty(value = "(24h)涨跌幅")
    private Double rate24;

    @ApiModelProperty(value = "(24h)交易额")
    private Double tradeTotal24;
}
