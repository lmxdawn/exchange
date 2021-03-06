package com.lmxdawn.dubboapi.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class PairDubboRes implements Serializable {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "交易币种ID")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    private Long coinId;

    @ApiModelProperty(value = "买入手续费率")
    private Double buyFee;

    @ApiModelProperty(value = "买入手续费精度")
    private Integer buyFeePrecision;

    @ApiModelProperty(value = "卖出手续费率")
    private Double sellFee;

    @ApiModelProperty(value = "卖出手续费精度")
    private Integer sellFeePrecision;

    @ApiModelProperty(value = "最低交易数量")
    private Double minAmount;

    @ApiModelProperty(value = "最低交易额")
    private Double minTotal;

    @ApiModelProperty(value = "交易额精度")
    private Integer tradeTotalPrecision;

    @ApiModelProperty(value = "价格精度")
    private Integer tradePricePrecision;

    @ApiModelProperty(value = "买入量精度")
    private Integer tradeAmountPrecision;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "机器人配置")
    private PairRobotDubboRes robotDubboRes;

}
