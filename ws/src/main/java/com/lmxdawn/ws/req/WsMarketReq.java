package com.lmxdawn.ws.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ws 行情消息推送
 */
@Data
public class WsMarketReq implements Serializable {

    @ApiModelProperty(value = "委托单变化的用户ID")
    private Long memberId;

    @ApiModelProperty(value = "委托单变化的订单号")
    private Long orderId;

    @ApiModelProperty(value = "对手委托单变化的用户ID")
    private Long matchMemberId;

    @ApiModelProperty(value = "对手委托单变化的订单号")
    private Long matchOrderId;

    @ApiModelProperty(value = "数据")
    private String Data;

}
