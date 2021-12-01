package com.lmxdawn.dubboapi.service.market;

import com.lmxdawn.dubboapi.req.market.SymbolQueryDubboReq;
import com.lmxdawn.dubboapi.req.market.SymbolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.market.SymbolDubboRes;
import com.lmxdawn.dubboapi.res.market.SymbolSimpleDubboRes;

/**
 * 交易对
 */
public interface SymbolDubboService {

    PageSimpleDubboRes<SymbolDubboRes> list(SymbolQueryDubboReq req);

    Long insert(SymbolSaveDubboReq req);

    boolean update(SymbolSaveDubboReq req);

    SymbolSimpleDubboRes findByTidAndCid(Long tradeCoinId, Long coinId);
}
