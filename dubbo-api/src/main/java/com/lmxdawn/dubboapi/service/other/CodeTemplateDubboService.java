package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.CodeTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.CodeTemplateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.CodeTemplateInfoDubboRes;

/**
 * 短信模板
 */
public interface CodeTemplateDubboService {

    PageSimpleDubboRes<CodeTemplateInfoDubboRes> list(CodeTemplateQueryDubboReq req);

    Long insert(CodeTemplateSaveDubboReq req);

    boolean update(CodeTemplateSaveDubboReq req);

    boolean delete(Long id);

}
