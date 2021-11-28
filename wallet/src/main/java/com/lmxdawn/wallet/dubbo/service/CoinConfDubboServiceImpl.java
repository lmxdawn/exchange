package com.lmxdawn.wallet.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.wallet.CoinConfQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinConfSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinConfDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinConfDubboService;
import com.lmxdawn.wallet.dao.CoinConfDao;
import com.lmxdawn.wallet.entity.CoinConf;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@DubboService
public class CoinConfDubboServiceImpl implements CoinConfDubboService {

    @Autowired
    private CoinConfDao coinConfDao;

    @Override
    public PageSimpleDubboRes<CoinConfDubboRes> list(CoinConfQueryDubboReq req) {

        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<CoinConfDubboRes> infoDubboResList = coinConfDao.listPageDubbo(req);

        PageSimpleDubboRes<CoinConfDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<CoinConfDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(CoinConfSaveDubboReq req) {

        Long coinId = req.getCoinId();
        Long protocolId = req.getProtocolId();
        CoinConf byCoinConf = coinConfDao.findByCoinIdAndProtocolId(coinId, protocolId);
        if (byCoinConf != null) {
            return null;
        }

        CoinConf coin = new CoinConf();
        BeanUtils.copyProperties(req, coin);
        coin.setCreateTime(new Date());
        coin.setModifiedTime(new Date());
        coinConfDao.insert(coin);
        return coin.getId();
    }

    @Override
    public boolean update(CoinConfSaveDubboReq req) {

        Long coinId = req.getCoinId();
        Long protocolId = req.getProtocolId();
        CoinConf byCoinConf = coinConfDao.findByCoinIdAndProtocolId(coinId, protocolId);
        if (byCoinConf != null && !byCoinConf.getId().equals(req.getId())) {
            return false;
        }
        CoinConf coin = new CoinConf();
        BeanUtils.copyProperties(req, coin);
        coin.setModifiedTime(new Date());

        return coinConfDao.update(coin);
    }
}
