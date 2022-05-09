package com.lmxdawn.trade.service;

import com.lmxdawn.trade.entity.Pair;
import com.lmxdawn.trade.req.PairListPageReq;
import com.lmxdawn.trade.req.PairReadReq;
import com.lmxdawn.trade.res.PairRes;

import java.util.List;

public interface PairService {

    List<PairRes> listPage(PairListPageReq req);

    Pair findByTidAndCid(Long tradeCoinId, Long coinId);

    PairRes read(PairReadReq req);

}
