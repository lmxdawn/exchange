package com.lmxdawn.trade.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.trade.dao.SymbolDao;
import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.req.SymbolListPageReq;
import com.lmxdawn.trade.req.SymbolReadReq;
import com.lmxdawn.trade.res.SymbolRes;
import com.lmxdawn.trade.service.SymbolService;
import com.lmxdawn.trade.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
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
    public List<SymbolRes> listPage(SymbolListPageReq req) {

        List<Symbol> symbols = new ArrayList<>();
        Long coinId = req.getCoinId();
        if (coinId == null || coinId == 0) {
            String collect = req.getCollect();
            List<Long> ids = new ArrayList<>();
            if (!StringUtils.isBlank(collect)) {
                String[] split = collect.split(",");
                for (String c : split) {
                    if (!StringUtils.isBlank(c)) {
                        ids.add(Long.valueOf(c));
                    }
                }
            }
            if (ids.size() > 0) {
                symbols = symbolDao.listByIdIn(ids);
            }

        } else {
            Integer page = req.getPage();
            Integer limit = req.getLimit();
            Integer offset = PageUtils.createOffset(page, limit);
            req.setOffset(offset);
            symbols = symbolDao.listPage(req);
        }


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

    @Override
    public SymbolRes read(SymbolReadReq req) {

        Symbol byTidAndCid = findByTidAndCid(req.getTradeCoinId(), req.getCoinId());

        SymbolRes symbolRes = new SymbolRes();
        if (byTidAndCid == null) {
            return symbolRes;
        }

        BeanUtils.copyProperties(byTidAndCid, symbolRes);

        Long tradeCoinId = byTidAndCid.getTradeCoinId();
        Long coinId = byTidAndCid.getCoinId();

        Set<Long> coinIdSet = new HashSet<>();
        coinIdSet.add(tradeCoinId);
        coinIdSet.add(coinId);
        List<Long> coinIds = new ArrayList<>(coinIdSet);

        Map<Long, CoinSimpleDubboRes> coinMap = coinDubboService.mapByCoinIds(coinIds);

        symbolRes.setTradeCoin(coinMap.get(tradeCoinId));
        symbolRes.setCoin(coinMap.get(coinId));

        return symbolRes;
    }
}
