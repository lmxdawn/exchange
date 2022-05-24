package com.lmxdawn.market.service;

import com.lmxdawn.market.req.KLineListReq;
import com.lmxdawn.market.res.KLineListRes;

import java.math.BigDecimal;
import java.util.List;

public interface KLineService {

    /**
     * 创建k线
     * @param tradeCoinId 交易币种ID
     * @param coinId 计价币种ID
     * @param price 价格
     * @param amount 交易量
     * @return
     */
    boolean install(Long tradeCoinId, Long coinId, BigDecimal price, BigDecimal amount);

    List<KLineListRes> list(KLineListReq req);
}
