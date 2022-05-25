package com.lmxdawn.market.mq;

import lombok.Data;

/**
 * 撮合明细
 */
@Data
public class MatchDetailMq {

    /**
     * 交易币种ID
     */
    private Long tradeCoinId;

    /**
     * 计价币种ID
     */
    private Long coinId;

    /**
     * 订单号
     */
    private Long id;

    /**
     * 对手订单号
     */
    private Long matchId;

    /**
     * 类型（1：限价，2：市价）
     */
    private Integer type;

    /**
     * 对手单类型（1：限价，2：市价）
     */
    private Integer matchType;

    /**
     * 方向（1：买入，2：卖出）
     */
    private Integer direction;

    /**
     * 对手单方向（1：买入，2：卖出）
     */
    private Integer matchDirection;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 对手单用户ID
     */
    private Long matchMemberId;

    /**
     * 对手单价格
     */
    private Double matchPrice;

    /**
     * 成交价格
     */
    private Double price;

    /**
     * 成交数量
     */
    private Double amount;

    /**
     * 是否完成（0：否，1：是）
     */
    private Integer isComplete;

    /**
     * 对手单是否完成（0：否，1：是）
     */
    private Integer matchIsComplete;

    /**
     * 是否是机器人（0：否，1：是）
     */
    private Integer isRobot;

    /**
     * 对手单是否是机器人（0：否，1：是）
     */
    private Integer matchIsRobot;

    /**
     * 买入手续费率
     */
    private Double buyFee;

    /**
     * 买入手续费精度
     */
    private Integer buyFeePrecision;

    /**
     * 卖出手续费率
     */
    private Double sellFee;

    /**
     * 卖出手续费精度
     */
    private Integer sellFeePrecision;

    /**
     * 交易量精度
     */
    private Integer tradeAmountPrecision;

}
