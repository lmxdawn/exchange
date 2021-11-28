package com.lmxdawn.wallet.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.wallet.CoinProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinProtocolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinProtocolDubboService;
import com.lmxdawn.wallet.dao.CoinProtocolDao;
import com.lmxdawn.wallet.entity.CoinProtocol;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DubboService
public class CoinProtocolDubboServiceImpl implements CoinProtocolDubboService {

    @Autowired
    private CoinProtocolDao coinProtocolDao;

    @Override
    public List<CoinProtocolSimpleDubboRes> listAll() {
        List<CoinProtocol> coins = coinProtocolDao.listAll();
        List<CoinProtocolSimpleDubboRes> collect = coins.stream().map(v -> {
            CoinProtocolSimpleDubboRes coinSimpleDubboRes = new CoinProtocolSimpleDubboRes();
            BeanUtils.copyProperties(v, coinSimpleDubboRes);
            return coinSimpleDubboRes;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PageSimpleDubboRes<CoinProtocolDubboRes> list(CoinProtocolQueryDubboReq req) {

        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<CoinProtocolDubboRes> infoDubboResList = coinProtocolDao.listPageDubbo(req);

        PageSimpleDubboRes<CoinProtocolDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<CoinProtocolDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(CoinProtocolSaveDubboReq req) {

        String name = req.getName();
        CoinProtocol byName = coinProtocolDao.findByName(name);
        if (byName != null) {
            return null;
        }

        CoinProtocol coin = new CoinProtocol();
        BeanUtils.copyProperties(req, coin);
        coin.setCreateTime(new Date());
        coin.setModifiedTime(new Date());
        coinProtocolDao.insert(coin);
        return coin.getId();
    }

    @Override
    public boolean update(CoinProtocolSaveDubboReq req) {

        String name = req.getName();
        CoinProtocol byName = coinProtocolDao.findByName(name);
        if (byName != null && !byName.getId().equals(req.getId())) {
            return false;
        }
        CoinProtocol coin = new CoinProtocol();
        BeanUtils.copyProperties(req, coin);
        coin.setModifiedTime(new Date());

        return coinProtocolDao.update(coin);
    }
}
