package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.QuestionHotQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.QuestionHotSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.QuestionHotInfoDubboRes;

/**
 * 热门问题
 */
public interface QuestionHotDubboService {

    PageSimpleDubboRes<QuestionHotInfoDubboRes> list(QuestionHotQueryDubboReq req);

    Long insert(QuestionHotSaveDubboReq req);

    boolean update(QuestionHotSaveDubboReq req);
}
