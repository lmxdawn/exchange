package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.SystemAppVersionQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SystemAppVersionSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SystemAppVersionInfoDubboRes;

/**
 * app版本
 */
public interface SystemAppVersionDubboService {

    PageSimpleDubboRes<SystemAppVersionInfoDubboRes> list(SystemAppVersionQueryDubboReq req);

    Long insert(SystemAppVersionSaveDubboReq req);

    boolean update(SystemAppVersionSaveDubboReq req);
}
