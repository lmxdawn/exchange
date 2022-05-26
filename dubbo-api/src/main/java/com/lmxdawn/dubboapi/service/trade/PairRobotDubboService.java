package com.lmxdawn.dubboapi.service.trade;

import com.lmxdawn.dubboapi.req.trade.PairRobotSaveDubboReq;
import com.lmxdawn.dubboapi.res.trade.PairRobotDubboRes;

import java.util.List;

/**
 * 交易对机器人
 */
public interface PairRobotDubboService {

    List<PairRobotDubboRes> listAll();

    Long insert(PairRobotSaveDubboReq req);

    boolean update(PairRobotSaveDubboReq req);

    PairRobotDubboRes findByTidAndCid(Long tradeCoinId, Long coinId);
}
