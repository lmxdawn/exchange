package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.SmsTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SmsTemplateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SmsTemplateInfoDubboRes;

/**
 * 短信模板
 */
public interface SmsTemplateDubboService {

    PageSimpleDubboRes<SmsTemplateInfoDubboRes> list(SmsTemplateQueryDubboReq smsTemplateQueryDubboReq);

    Long insert(SmsTemplateSaveDubboReq smsTemplateSaveDubboReq);

    boolean update(SmsTemplateSaveDubboReq smsTemplateSaveDubboReq);

    boolean delete(Long id);

}
