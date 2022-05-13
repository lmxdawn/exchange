package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.AreaCodeQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.AreaCodeSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.AreaCodeInfoDubboRes;

/**
 * 电话地区
 */
public interface AreaCodeDubboService {

    PageSimpleDubboRes<AreaCodeInfoDubboRes> list(AreaCodeQueryDubboReq req);

    Long insert(AreaCodeSaveDubboReq req);

    boolean update(AreaCodeSaveDubboReq req);
}
