package com.lmxdawn.dubboapi.req.trade;

import lombok.Data;

import java.io.Serializable;

/**
 * 委托订单撮合成功
 */
@Data
public class EntrustOrderMatchDubboReq implements Serializable {

    /**
     * 订单号
     */
    private Long id;

    /**
     * 上次已完成数量，幂等需要
     */
    private Double amountComplete;

    /**
     * 成交数量
     */
    private Double amount;

    /**
     * 成交额
     */
    private Double total;

    /**
     * 状态（1：未完成，2：已完成）
     */
    private Integer status;

    /**
     * 对手订单号
     */
    private Long matchId;

    /**
     * 对手单上次已完成数量，幂等需要
     */
    private Double matchAmountComplete;

    /**
     * 对手单成交数量
     */
    private Double matchAmount;

    /**
     * 对手单成交额
     */
    private Double matchTotal;

    /**
     * 对手单状态（1：未完成，2：已完成）
     */
    private Integer matchStatus;


}
