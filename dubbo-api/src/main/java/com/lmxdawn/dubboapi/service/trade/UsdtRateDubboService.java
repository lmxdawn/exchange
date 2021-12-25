package com.lmxdawn.dubboapi.service.trade;

import com.lmxdawn.dubboapi.req.trade.UsdtRateQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.UsdtRateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.UsdtRateDubboRes;

/**
 * USDT汇率
 */
public interface UsdtRateDubboService {

    PageSimpleDubboRes<UsdtRateDubboRes> list(UsdtRateQueryDubboReq req);

    Long insert(UsdtRateSaveDubboReq req);

    boolean update(UsdtRateSaveDubboReq req);

}
