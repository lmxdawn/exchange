package com.lmxdawn.trade.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.trade.dao.PairDao;
import com.lmxdawn.trade.entity.Pair;
import com.lmxdawn.trade.req.PairListPageReq;
import com.lmxdawn.trade.req.PairReadReq;
import com.lmxdawn.trade.res.PairRes;
import com.lmxdawn.trade.service.PairService;
import com.lmxdawn.trade.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PairServiceImpl implements PairService {

    @Autowired
    private PairDao pairDao;

    @DubboReference
    private CoinDubboService coinDubboService;

    @Override
    public List<PairRes> listPage(PairListPageReq req) {

        List<Pair> pairs = new ArrayList<>();
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
                pairs = pairDao.listByIdIn(ids);
            }

        } else {
            Integer page = req.getPage();
            Integer limit = req.getLimit();
            Integer offset = PageUtils.createOffset(page, limit);
            req.setOffset(offset);
            pairs = pairDao.listPage(req);
        }


        if (pairs.size() == 0) {
            return new ArrayList<>();
        }

        Set<Long> coinIdSet = new HashSet<>();
        pairs.forEach(v -> {
            coinIdSet.add(v.getTradeCoinId());
            coinIdSet.add(v.getCoinId());
        });
        List<Long> coinIds = new ArrayList<>(coinIdSet);

        Map<Long, CoinSimpleDubboRes> coinMap = coinDubboService.mapByCoinIds(coinIds);

        List<PairRes> collect = pairs.stream().map(v -> {
            PairRes pairRes = new PairRes();
            BeanUtils.copyProperties(v, pairRes);
            pairRes.setTradeCoin(coinMap.get(v.getTradeCoinId()));
            pairRes.setCoin(coinMap.get(v.getCoinId()));
            return pairRes;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public Pair findByTidAndCid(Long tradeCoinId, Long coinId) {
        return pairDao.findByTidAndCid(tradeCoinId, coinId);
    }

    @Override
    public PairRes read(PairReadReq req) {

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();

        Pair byTidAndCid = findByTidAndCid(tradeCoinId, coinId);

        PairRes pairRes = new PairRes();
        if (byTidAndCid == null) {
            return pairRes;
        }

        BeanUtils.copyProperties(byTidAndCid, pairRes);

        Set<Long> coinIdSet = new HashSet<>();
        coinIdSet.add(tradeCoinId);
        coinIdSet.add(coinId);
        List<Long> coinIds = new ArrayList<>(coinIdSet);

        Map<Long, CoinSimpleDubboRes> coinMap = coinDubboService.mapByCoinIds(coinIds);

        pairRes.setTradeCoin(coinMap.get(tradeCoinId));
        pairRes.setCoin(coinMap.get(coinId));

        return pairRes;
    }
}
