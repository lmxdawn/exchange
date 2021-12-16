package com.lmxdawn.market.ws;

import lombok.Data;

@Data
public class MatchVo {

    // 交易币种ID
    private Long tradeCoinId;

    // 计价币种ID
    private Long coinId;

    // 数量
    private Double amount;

    // 价格
    private Double price;

}
