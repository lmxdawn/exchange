package com.lmxdawn.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

// 查询委托订单详情
@ApiModel
@Data
public class EntrustOrderReadReq {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "订单ID必传")
    private Long id;

}
