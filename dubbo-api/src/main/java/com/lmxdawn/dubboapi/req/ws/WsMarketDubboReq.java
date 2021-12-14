package com.lmxdawn.dubboapi.req.ws;

import lombok.Data;

import java.io.Serializable;

/**
 * ws 行情消息推送
 */
@Data
public class WsMarketDubboReq implements Serializable {

    /**
     * 委托单变化的用户ID
     */
    private Long memberId;

    /**
     * 委托单变化的订单号
     */
    private Long orderId;

    /**
     * 数据
     */
    private String Data;

}
