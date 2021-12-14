package com.lmxdawn.wsroute.mq;

import lombok.Data;

@Data
public class WsMarketMq {

    /**
     * 委托单变化的用户ID
     */
    private Long memberId;

    /**
     * 委托单变化的订单号
     */
    private Long orderId;

    /**
     * 对手委托单变化的用户ID
     */
    private Long matchMemberId;

    /**
     * 对手委托单变化的订单号
     */
    private Long matchOrderId;

    /**
     * 数据
     */
    private String Data;

}
