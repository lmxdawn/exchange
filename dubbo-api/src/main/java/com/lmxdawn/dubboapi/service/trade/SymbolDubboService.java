package com.lmxdawn.dubboapi.service.trade;

import com.lmxdawn.dubboapi.req.trade.SymbolQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.SymbolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.SymbolDubboRes;
import com.lmxdawn.dubboapi.res.trade.SymbolSimpleDubboRes;

/**
 * 交易对
 */
public interface SymbolDubboService {

    PageSimpleDubboRes<SymbolDubboRes> list(SymbolQueryDubboReq req);

    Long insert(SymbolSaveDubboReq req);

    boolean update(SymbolSaveDubboReq req);

    SymbolSimpleDubboRes findByTidAndCid(Long tradeCoinId, Long coinId);
}
