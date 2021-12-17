package com.lmxdawn.dubboapi.req.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改账户
 */
@Data
public class MemberCoinMatchDubboReq implements Serializable {

    /**
     * 交易币种ID
     */
    private Long tradeCoinId;

    /**
     * 计价币种ID
     */
    private Long coinId;

    /**
     * 买入的用户ID
     */
    private Long buyMemberId;

    /**
     * 买入的总额
     */
    private Double buyMoney;

    /**
     * 买入的手续费
     */
    private Double buyMoneyFee;

    /**
     * 买入的解冻总额
     */
    private Double buyUnfrozenMoney;

    /**
     * 卖出的用户ID
     */
    private Long sellMemberId;

    /**
     * 卖出得到的总额
     */
    private Double sellMoney;

    /**
     * 卖出的手续费
     */
    private Double sellMoneyFee;

    /**
     * 卖出的总额
     */
    private Double sellUnfrozenMoney;

}
