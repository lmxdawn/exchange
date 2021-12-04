package com.lmxdawn.trade.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.trade.dao.SymbolDao;
import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.res.SymbolRes;
import com.lmxdawn.trade.service.SymbolService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SymbolServiceImpl implements SymbolService {

    @Autowired
    private SymbolDao symbolDao;

    @DubboReference
    private CoinDubboService coinDubboService;

    @Override
    public List<SymbolRes> listAll() {

        List<Symbol> symbols = symbolDao.listAll();

        if (symbols.size() == 0) {
            return new ArrayList<>();
        }

        Set<Long> coinIdSet = new HashSet<>();
        symbols.forEach(v -> {
            coinIdSet.add(v.getTradeCoinId());
            coinIdSet.add(v.getCoinId());
        });
        List<Long> coinIds = new ArrayList<>(coinIdSet);

        Map<Long, CoinSimpleDubboRes> coinMap = coinDubboService.mapByCoinIds(coinIds);

        List<SymbolRes> collect = symbols.stream().map(v -> {
            SymbolRes symbolRes = new SymbolRes();
            BeanUtils.copyProperties(v, symbolRes);
            symbolRes.setTradeCoin(coinMap.get(v.getTradeCoinId()));
            symbolRes.setCoin(coinMap.get(v.getCoinId()));
            return symbolRes;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public Symbol findByTidAndCid(Long tradeCoinId, Long coinId) {
        return symbolDao.findByTidAndCid(tradeCoinId, coinId);
    }
}
