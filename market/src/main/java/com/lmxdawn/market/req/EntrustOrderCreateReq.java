package com.lmxdawn.market.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 创建委托订单
@ApiModel
@Data
public class EntrustOrderCreateReq {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种不能为空")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种不能为空")
    private Long coinId;

    @ApiModelProperty(value = "类型（1：限价，2：市价）")
    @NotNull(message = "类型不能为空")
    @Min(value = 1, message = "类型格式错误")
    @Max(value = 2, message = "类型格式错误")
    private Integer type;

    @ApiModelProperty(value = "方向（1：买入，2：卖出）")
    @NotNull(message = "方向不能为空")
    @Min(value = 1, message = "方向格式错误")
    @Max(value = 2, message = "方向格式错误")
    private Integer direction;

    @ApiModelProperty(value = "价格（如果是市价则为空）")
    private Double price;

    @ApiModelProperty(value = "价格（如果是市价则为空）")
    @NotNull(message = "数量不能为空")
    private Double amount;
}
