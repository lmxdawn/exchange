package com.lmxdawn.dubboapi.res.market;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class DepthSizeDubboRes implements Serializable {

    @ApiModelProperty(value = "买盘订单数量")
    private Integer buySize;

    @ApiModelProperty(value = "买盘中随机的价格")
    private BigDecimal buyRandPrice;

    @ApiModelProperty(value = "买盘第一个价格")
    private BigDecimal buyFirstPrice;

    @ApiModelProperty(value = "买盘第一个价格的数量")
    private BigDecimal buyFirstAmount;

    @ApiModelProperty(value = "买盘大于最新价格的挂单数量")
    private BigDecimal buyGreaterPriceAmount;

    @ApiModelProperty(value = "买盘最后一个价格")
    private BigDecimal buyLastPrice;

    @ApiModelProperty(value = "卖盘订单数量")
    private Integer sellSize;

    @ApiModelProperty(value = "卖盘中随机的价格")
    private BigDecimal sellRandPrice;

    @ApiModelProperty(value = "卖盘第一个价格")
    private BigDecimal sellFirstPrice;

    @ApiModelProperty(value = "卖盘中第一个价格的数量")
    private BigDecimal sellFirstAmount;

    @ApiModelProperty(value = "卖盘大于最新价格的挂单数量")
    private BigDecimal sellGreaterPriceAmount;

    @ApiModelProperty(value = "卖盘最后一个价格")
    private BigDecimal sellLastPrice;

}
