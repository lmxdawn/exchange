package com.lmxdawn.market.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.market.SymbolQueryDubboReq;
import com.lmxdawn.dubboapi.req.market.SymbolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.market.SymbolDubboRes;
import com.lmxdawn.dubboapi.res.market.SymbolSimpleDubboRes;
import com.lmxdawn.dubboapi.service.market.SymbolDubboService;
import com.lmxdawn.market.dao.SymbolDao;
import com.lmxdawn.market.entity.Symbol;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@DubboService
public class SymbolDubboServiceImpl implements SymbolDubboService {

    @Autowired
    private SymbolDao symbolDao;

    @Override
    public PageSimpleDubboRes<SymbolDubboRes> list(SymbolQueryDubboReq req) {

        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<SymbolDubboRes> infoDubboResList = symbolDao.listPageDubbo(req);

        PageSimpleDubboRes<SymbolDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<SymbolDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(SymbolSaveDubboReq req) {

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();
        Symbol byTidAndCid = symbolDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid != null) {
            return null;
        }

        Symbol coin = new Symbol();
        BeanUtils.copyProperties(req, coin);
        coin.setCreateTime(new Date());
        coin.setModifiedTime(new Date());
        symbolDao.insert(coin);
        return coin.getId();
    }

    @Override
    public boolean update(SymbolSaveDubboReq req) {

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();
        Symbol byTidAndCid = symbolDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid != null && !byTidAndCid.getId().equals(req.getId())) {
            return false;
        }
        Symbol coin = new Symbol();
        BeanUtils.copyProperties(req, coin);
        coin.setModifiedTime(new Date());

        return symbolDao.update(coin);
    }

    @Override
    public SymbolSimpleDubboRes findByTidAndCid(Long tradeCoinId, Long coinId) {
        Symbol byTidAndCid = symbolDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid == null) {
            return null;
        }
        SymbolSimpleDubboRes symbolSimpleDubboRes = new SymbolSimpleDubboRes();
        BeanUtils.copyProperties(byTidAndCid, symbolSimpleDubboRes);
        return symbolSimpleDubboRes;
    }
}
