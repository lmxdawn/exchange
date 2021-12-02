package com.lmxdawn.wallet.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.wallet.CoinQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.ProtocolInfoDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.wallet.dao.CoinDao;
import com.lmxdawn.wallet.entity.Coin;
import com.lmxdawn.wallet.util.PageUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DubboService
public class CoinDubboServiceImpl implements CoinDubboService {

    @Autowired
    private CoinDao coinDao;

    @Override
    public List<CoinSimpleDubboRes> listAll() {
        List<Coin> coins = coinDao.listAll();
        List<CoinSimpleDubboRes> collect = coins.stream().map(v -> {
            CoinSimpleDubboRes coinSimpleDubboRes = new CoinSimpleDubboRes();
            BeanUtils.copyProperties(v, coinSimpleDubboRes);
            return coinSimpleDubboRes;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PageSimpleDubboRes<CoinDubboRes> list(CoinQueryDubboReq req) {

        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<CoinDubboRes> infoDubboResList = coinDao.listPageDubbo(req);

        PageSimpleDubboRes<CoinDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<CoinDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Map<Long, CoinSimpleDubboRes> mapByCoinIds(List<Long> coinIds) {
        Map<Long, CoinSimpleDubboRes> map = new HashMap<>();
        if (coinIds == null || coinIds.size() == 0) {
            return map;
        }
        List<Coin> coins = coinDao.listByIdIn(coinIds);
        coins.forEach(v -> {
            CoinSimpleDubboRes coinSimpleDubboRes = new CoinSimpleDubboRes();
            BeanUtils.copyProperties(v, coinSimpleDubboRes);
            map.put(v.getId(), coinSimpleDubboRes);
        });
        return map;
    }

    @Override
    public Long insert(CoinSaveDubboReq req) {

        String name = req.getName();
        Coin byName = coinDao.findByName(name);
        if (byName != null) {
            return null;
        }

        Coin coin = new Coin();
        BeanUtils.copyProperties(req, coin);
        coin.setCreateTime(new Date());
        coin.setModifiedTime(new Date());
        coinDao.insert(coin);
        return coin.getId();
    }

    @Override
    public boolean update(CoinSaveDubboReq req) {

        String coinName = req.getCoinName();
        Coin byName = coinDao.findByName(coinName);
        if (byName != null && !byName.getId().equals(req.getId())) {
            return false;
        }
        Coin coin = new Coin();
        BeanUtils.copyProperties(req, coin);
        coin.setModifiedTime(new Date());

        return coinDao.update(coin);
    }
}
