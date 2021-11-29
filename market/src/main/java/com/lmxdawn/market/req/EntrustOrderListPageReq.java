package com.lmxdawn.market.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// 查询委托订单列表
@ApiModel
@Data
public class EntrustOrderListPageReq {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种不能为空")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种不能为空")
    private Long coinId;

    @ApiModelProperty(value = "状态（1：未完成，2：已完成）")
    private Integer status;

}
