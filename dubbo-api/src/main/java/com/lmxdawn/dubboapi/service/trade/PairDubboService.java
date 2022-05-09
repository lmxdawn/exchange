package com.lmxdawn.dubboapi.service.trade;

import com.lmxdawn.dubboapi.req.trade.PairQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.PairSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.PairDubboRes;
import com.lmxdawn.dubboapi.res.trade.PairSimpleDubboRes;

/**
 * 交易对
 */
public interface PairDubboService {

    PageSimpleDubboRes<PairDubboRes> list(PairQueryDubboReq req);

    Long insert(PairSaveDubboReq req);

    boolean update(PairSaveDubboReq req);

    PairSimpleDubboRes findByTidAndCid(Long tradeCoinId, Long coinId);
}
