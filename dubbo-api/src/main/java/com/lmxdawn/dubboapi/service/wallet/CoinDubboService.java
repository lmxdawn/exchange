package com.lmxdawn.dubboapi.service.wallet;

import com.lmxdawn.dubboapi.req.wallet.CoinQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;

import java.util.List;
import java.util.Map;

/**
 * 币种
 */
public interface CoinDubboService {

    List<CoinSimpleDubboRes> listAll();

    PageSimpleDubboRes<CoinDubboRes> list(CoinQueryDubboReq req);

    Map<Long, CoinSimpleDubboRes> mapByCoinIds(List<Long> coinIds);

    Long insert(CoinSaveDubboReq req);

    boolean update(CoinSaveDubboReq req);
}
