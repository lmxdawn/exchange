package com.lmxdawn.trade.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.trade.UsdtRateQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.UsdtRateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.UsdtRateDubboRes;
import com.lmxdawn.dubboapi.service.trade.UsdtRateDubboService;
import com.lmxdawn.trade.dao.UsdtRateDao;
import com.lmxdawn.trade.entity.UsdtRate;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@DubboService
public class UsdtRateDubboServiceImpl implements UsdtRateDubboService {

    @Autowired
    private UsdtRateDao usdtRateDao;

    @Override
    public PageSimpleDubboRes<UsdtRateDubboRes> list(UsdtRateQueryDubboReq req) {

        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<UsdtRateDubboRes> infoDubboResList = usdtRateDao.listPageDubbo(req);

        PageSimpleDubboRes<UsdtRateDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<UsdtRateDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(UsdtRateSaveDubboReq req) {

        String name = req.getName();
        UsdtRate byName = usdtRateDao.findByName(name);
        if (byName != null) {
            return null;
        }

        UsdtRate usdtRate = new UsdtRate();
        BeanUtils.copyProperties(req, usdtRate);
        usdtRate.setCreateTime(new Date());
        usdtRate.setModifiedTime(new Date());
        usdtRateDao.insert(usdtRate);
        return usdtRate.getId();
    }

    @Override
    public boolean update(UsdtRateSaveDubboReq req) {

        String name = req.getName();
        UsdtRate byName = usdtRateDao.findByName(name);
        if (byName != null && !byName.getId().equals(req.getId())) {
            return false;
        }
        UsdtRate usdtRate = new UsdtRate();
        BeanUtils.copyProperties(req, usdtRate);
        usdtRate.setModifiedTime(new Date());

        return usdtRateDao.update(usdtRate);
    }
}
