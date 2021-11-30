package com.lmxdawn.market.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// 查询委托订单列表
@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class EntrustOrderListPageReq extends ListPageReq {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "交易币种ID")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    private Long coinId;

    @ApiModelProperty(value = "类型（0：全部，1：限价，2：市价）")
    private Integer direction;

    @ApiModelProperty(value = "状态（0：全部，1：未完成，2：已完成）")
    private Integer status;

}
