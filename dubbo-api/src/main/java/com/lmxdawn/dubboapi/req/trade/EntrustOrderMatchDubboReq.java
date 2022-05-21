package com.lmxdawn.dubboapi.req.trade;

import lombok.Data;

import java.io.Serializable;

/**
 * 委托订单撮合成功
 */
@Data
public class EntrustOrderMatchDubboReq implements Serializable {

    /**
     * 交易币种ID
     */
    private Long tradeCoinId;

    /**
     * 计价币种ID
     */
    private Long coinId;

    /**
     * 价格
     */
    private Double price;

    /**
     * 成交数量
     */
    private Double amount;

    /**
     * 成交额
     */
    private Double total;

    /*---------订单信息------------*/

    /**
     * 订单号
     */
    private Long id;

    /**
     * 方向（1：买入，2：卖出）
     */
    private Integer direction;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 上次已完成数量，幂等需要
     */
    private Double amountComplete;

    /**
     * 手续费
     */
    private Double fee;

    /**
     * 状态（1：未完成，2：已完成）
     */
    private Integer status;

    /**
     * 对手订单号
     */
    private Long matchId;

    /**
     * 对手单方向（1：买入，2：卖出）
     */
    private Integer matchDirection;

    /**
     * 对手单用户ID
     */
    private Long matchMemberId;

    /**
     * 对手单上次已完成数量，幂等需要
     */
    private Double matchAmountComplete;

    /**
     * 对手单手续费
     */
    private Double matchFee;

    /**
     * 对手单状态（1：未完成，2：已完成）
     */
    private Integer matchStatus;


}
