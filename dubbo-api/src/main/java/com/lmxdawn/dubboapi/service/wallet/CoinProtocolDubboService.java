package com.lmxdawn.dubboapi.service.wallet;

import com.lmxdawn.dubboapi.req.wallet.CoinProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinProtocolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolSimpleDubboRes;

import java.util.List;

/**
 * 币种协议
 */
public interface CoinProtocolDubboService {

    List<CoinProtocolSimpleDubboRes> listAll();

    PageSimpleDubboRes<CoinProtocolDubboRes> list(CoinProtocolQueryDubboReq req);

    Long insert(CoinProtocolSaveDubboReq req);

    boolean update(CoinProtocolSaveDubboReq req);
}
