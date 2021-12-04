package com.lmxdawn.trade.service;

import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.res.SymbolRes;

import java.util.List;

public interface SymbolService {

    List<SymbolRes> listAll();

    Symbol findByTidAndCid(Long tradeCoinId, Long coinId);

}
