package com.lmxdawn.dubboapi.req.match;

import lombok.Data;

import java.io.Serializable;

@Data
public class MatchEventDubboReq implements Serializable {

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
