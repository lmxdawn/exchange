package com.lmxdawn.trade.service;

import com.lmxdawn.trade.res.UsdtRateRes;

import java.util.List;

public interface UsdtRateService {

    List<UsdtRateRes> listAll();

    UsdtRateRes read(String name);

}
