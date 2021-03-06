package com.lmxdawn.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

// 查询委托订单明细列表
@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class EntrustOrderDetailReq extends ListPageReq {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

}
