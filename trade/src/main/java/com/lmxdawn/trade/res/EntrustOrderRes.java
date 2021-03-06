package com.lmxdawn.trade.res;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "委托订单")
@Data
public class EntrustOrderRes {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "交易币种")
    private CoinSimpleDubboRes tradeCoin;

    @ApiModelProperty(value = "计价币种")
    private CoinSimpleDubboRes coin;

    @ApiModelProperty(value = "类型（1：限价，2：市价）")
    private Integer type;

    @ApiModelProperty(value = "方向（1：买入，2：卖出）")
    private Integer direction;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "数量")
    private Double amount;

    @ApiModelProperty(value = "已完成数量")
    private Double amountComplete;

    @ApiModelProperty(value = "交易额")
    private Double total;

    @ApiModelProperty(value = "已完成交易额")
    private Double totalComplete;

    @ApiModelProperty(value = "状态（1：未完成，2：已完成，3：已撤单）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifiedTime;
}
