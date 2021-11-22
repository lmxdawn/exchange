package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.ProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.ProtocolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.ProtocolInfoDubboRes;

/**
 * 协议
 */
public interface ProtocolDubboService {

    PageSimpleDubboRes<ProtocolInfoDubboRes> list(ProtocolQueryDubboReq req);

    Long insert(ProtocolSaveDubboReq req);

    boolean update(ProtocolSaveDubboReq req);
}
