package com.lmxdawn.dubboapi.res.trade;

import lombok.Data;

import java.io.Serializable;

@Data
public class EntrustOrderMatchDubboRes implements Serializable {

    /**
     * 订单号
     */
    private Long id;

    /**
     * 数量
     */
    private Double amount;

    /**
     * 已完成数量
     */
    private Double amountComplete;

    /**
     * 交易额
     */
    private Double total;

    /**
     * 已完成的交易额
     */
    private Double totalComplete;

}
