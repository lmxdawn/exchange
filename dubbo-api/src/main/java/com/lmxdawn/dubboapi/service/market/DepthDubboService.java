package com.lmxdawn.dubboapi.service.market;

import com.lmxdawn.dubboapi.res.market.DepthSizeDubboRes;

import java.math.BigDecimal;

/**
 * 深度行情服务
 */
public interface DepthDubboService {

    DepthSizeDubboRes size(Long tradeCoinId, Long coinId, BigDecimal price, Integer initCount, Integer amountPrecision);

}
