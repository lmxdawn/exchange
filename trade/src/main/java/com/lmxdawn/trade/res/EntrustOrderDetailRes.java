package com.lmxdawn.trade.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "委托订单明细")
@Data
public class EntrustOrderDetailRes {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "手续费")
    private Double fee;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "数量")
    private Double amount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
