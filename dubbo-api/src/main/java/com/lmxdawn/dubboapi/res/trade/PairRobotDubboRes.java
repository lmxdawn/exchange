package com.lmxdawn.dubboapi.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class PairRobotDubboRes implements Serializable {

    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "交易对ID")
    private Long pairId;

    @ApiModelProperty(value = "交易币种ID")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    private Long coinId;

    @ApiModelProperty(value = "小写的交易对名称")
    private String lowerCoinName;

    @ApiModelProperty(value = "大写的交易对名称")
    private String upperCoinName;

    @ApiModelProperty(value = "最低交易量")
    private Double minAmount;

    @ApiModelProperty(value = "交易量随机数范围 1%概率")
    private Double randRange0;

    @ApiModelProperty(value = "交易量随机数范围 9%概率")
    private Double randRange1;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    private Double randRange2;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    private Double randRange3;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    private Double randRange4;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    private Double randRange5;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 10%概率")
    private Double randRange6;

    @ApiModelProperty(value = "价格精度")
    private Integer pricePrecision;

    @ApiModelProperty(value = "交易量精度")
    private Integer amountPrecision;

    @ApiModelProperty(value = "买盘最高价与卖盘最低价相差超过该值")
    private Double maxSubPrice;

    @ApiModelProperty(value = "初始订单量")
    private Integer initOrderCount;

    @ApiModelProperty(value = "价格变化步长(0.01 = 1%)")
    private Double priceStepRate;

    @ApiModelProperty(value = "行情请求间隔时间（单位毫秒 1000 = 1秒）")
    private Integer runTime;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

}
