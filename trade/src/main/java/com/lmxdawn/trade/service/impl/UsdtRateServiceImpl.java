package com.lmxdawn.trade.service.impl;

import com.lmxdawn.trade.dao.UsdtRateDao;
import com.lmxdawn.trade.entity.UsdtRate;
import com.lmxdawn.trade.res.UsdtRateRes;
import com.lmxdawn.trade.service.UsdtRateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsdtRateServiceImpl implements UsdtRateService {

    @Autowired
    private UsdtRateDao usdtRateDao;

    @Override
    public List<UsdtRateRes> listAll() {
        List<UsdtRate> usdtRates = usdtRateDao.listAll();
        List<UsdtRateRes> collect = usdtRates.stream().map(v -> {
            UsdtRateRes res = new UsdtRateRes();
            BeanUtils.copyProperties(v, res);
            return res;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public UsdtRateRes read(String name) {

        UsdtRate byName = usdtRateDao.findByName(name);

        UsdtRateRes usdtRateRes = new UsdtRateRes();
        if (byName == null) {
            return usdtRateRes;
        }
        BeanUtils.copyProperties(byName, usdtRateRes);
        return usdtRateRes;
    }
}
