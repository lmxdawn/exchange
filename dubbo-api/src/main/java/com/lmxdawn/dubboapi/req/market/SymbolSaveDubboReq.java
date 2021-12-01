package com.lmxdawn.dubboapi.req.market;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 交易对添加/修改
 */
@ApiModel
@Data
public class SymbolSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种不能为空")
    @Min(value = 1, message = "交易币种格式错误")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种不能为空")
    @Min(value = 1, message = "计价币种格式错误")
    private Long coinId;

    @ApiModelProperty(value = "手续费率")
    @Min(value = 0, message = "手续费率格式错误")
    private Double fee = 0.00;

    @ApiModelProperty(value = "手续费精度")
    @Min(value = 0, message = "手续费精度格式错误")
    private Integer feePrecision;

    @ApiModelProperty(value = "交易额精度")
    @Min(value = 0, message = "交易额精度格式错误")
    private Integer tradeTotalPrecision;

    @ApiModelProperty(value = "价格精度")
    @Min(value = 0, message = "价格精度格式错误")
    private Integer tradePricePrecision;

    @ApiModelProperty(value = "买入量精度")
    @Min(value = 0, message = "买入量精度格式错误")
    private Integer tradeAmountPrecision;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
