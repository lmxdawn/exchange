package com.lmxdawn.trade.service;

import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.req.SymbolListPageReq;
import com.lmxdawn.trade.res.SymbolRes;

import java.util.List;

public interface SymbolService {

    List<SymbolRes> listPage(SymbolListPageReq req);

    Symbol findByTidAndCid(Long tradeCoinId, Long coinId);

}
