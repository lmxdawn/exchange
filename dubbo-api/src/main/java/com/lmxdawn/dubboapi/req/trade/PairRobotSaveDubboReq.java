package com.lmxdawn.dubboapi.req.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 交易对机器人添加/修改
 */
@ApiModel
@Data
public class PairRobotSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "交易对ID")
    @NotNull(message = "交易对ID不能为空")
    @Min(value = 1, message = "交易币种格式错误")
    private Long pairId;

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种不能为空")
    @Min(value = 1, message = "交易币种格式错误")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种不能为空")
    @Min(value = 1, message = "计价币种格式错误")
    private Long coinId;

    @ApiModelProperty(value = "小写的交易对名称")
    private String lowerCoinName;

    @ApiModelProperty(value = "大写的交易对名称")
    private String upperCoinName;

    @ApiModelProperty(value = "最低交易数量")
    @Min(value = 0, message = "最低交易数量格式错误")
    private Double minAmount;

    @ApiModelProperty(value = "交易量随机数范围 1%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange0;

    @ApiModelProperty(value = "交易量随机数范围 9%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange1;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange2;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange3;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange4;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange5;

    @ApiModelProperty(value = "交易量随机数范围0.1(0.0001 ~ 0.09) 10%概率")
    @Min(value = 0, message = "交易量随机数范围格式错误")
    private Double randRange6;

    @ApiModelProperty(value = "价格精度")
    @Min(value = 0, message = "价格精度格式错误")
    private Integer pricePrecision;

    @ApiModelProperty(value = "买入量精度")
    @Min(value = 0, message = "买入量精度格式错误")
    private Integer amountPrecision;

    @ApiModelProperty(value = "买盘最高价与卖盘最低价相差超过该值")
    @NotNull(message = "买卖盘差价不能为空")
    private Double maxSubPrice;

    @ApiModelProperty(value = "初始订单量")
    @NotNull(message = "初始订单量不能为空")
    private Integer initOrderCount;

    @ApiModelProperty(value = "价格变化步长(0.01 = 1%)")
    @NotNull(message = "价格变化步长不能为空")
    private Double priceStepRate;

    @ApiModelProperty(value = "行情请求间隔时间（单位毫秒 1000 = 1秒）")
    @NotNull(message = "行情请求间隔时间不能为空")
    private Integer runTime;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
