package com.lmxdawn.match.disruptor;

import lombok.Data;

@Data
public class MatchEvent {

    /**
     * 订单号
     */
    private Long id;

    /**
     * 用户ID
      */
    private Long memberId;
    /**
     * 交易币种ID
      */
    private Long tradeCoinId;
    /**
     * 计价币种ID
     */
    private Long coinId;
    /**
     * 类型（1：限价，2：市价）
     */
    private Integer type;
    /**
     * 方向（1：买入，2：卖出）
     */
    private Integer direction;
    /**
     * 价格（如果是市价则为空）
     */
    private Double price;
    /**
     * 数量
     */
    private Double amount;
    /**
     * 交易金额
     */
    private Double total;

    /**
     * 交易量精度
     */
    private Integer tradeAmountPrecision;
}
