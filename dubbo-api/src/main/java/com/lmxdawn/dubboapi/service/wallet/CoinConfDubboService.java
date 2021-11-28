package com.lmxdawn.dubboapi.service.wallet;

import com.lmxdawn.dubboapi.req.wallet.CoinConfQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinConfSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinConfDubboRes;

/**
 * 币种配置
 */
public interface CoinConfDubboService {

    PageSimpleDubboRes<CoinConfDubboRes> list(CoinConfQueryDubboReq req);

    Long insert(CoinConfSaveDubboReq req);

    boolean update(CoinConfSaveDubboReq req);
}
