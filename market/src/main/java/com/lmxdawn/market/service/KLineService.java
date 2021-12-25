package com.lmxdawn.market.service;

import java.math.BigDecimal;

public interface KLineService {

    /**
     * 创建k线
     * @param tradeCoinId 交易币种ID
     * @param coinId 计价币种ID
     * @param price 价格
     * @param amount 以基础币种计量的交易量
     * @param vol 以报价币种计量的交易量
     * @return
     */
    boolean install(Long tradeCoinId, Long coinId, BigDecimal price, BigDecimal amount, BigDecimal vol);
}
