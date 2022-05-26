package com.lmxdawn.dubboapi.req.trade;

import lombok.Data;

import java.io.Serializable;

/**
 * 委托订单创建
 */
@Data
public class EntrustOrderRobotCreateDubboReq implements Serializable {

    /**
     * 交易币种ID
     * */
    private Long tradeCoinId;
    /**
     * 计价币种ID
     * */
    private Long coinId;
    /**
     * 类型（1：限价，2：市价）
     * */
    private Integer type;
    /**
     * 方向（1：买入，2：卖出）
     * */
    private Integer direction;
    /**
     * 价格（如果是市价则为空）
     * */
    private Double price;
    /**
     * 数量
     * */
    private Double amount;


}
