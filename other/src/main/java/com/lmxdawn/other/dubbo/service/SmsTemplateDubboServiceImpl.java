package com.lmxdawn.other.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.SmsTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SmsTemplateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SmsTemplateInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.SmsTemplateDubboService;
import com.lmxdawn.other.dao.SmsTemplateDao;
import com.lmxdawn.other.entity.SmsTemplate;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 配置服务
 */
@DubboService
public class SmsTemplateDubboServiceImpl implements SmsTemplateDubboService {

    @Autowired
    private SmsTemplateDao smsTemplateDao;

    @Override
    public PageSimpleDubboRes<SmsTemplateInfoDubboRes> list(SmsTemplateQueryDubboReq smsTemplateQueryDubboReq) {
        int offset = (smsTemplateQueryDubboReq.getPage() - 1) * smsTemplateQueryDubboReq.getLimit();
        PageHelper.offsetPage(offset, smsTemplateQueryDubboReq.getLimit());
        List<SmsTemplateInfoDubboRes> smsTemplateInfoDubboRes = smsTemplateDao.listPageDubbo(smsTemplateQueryDubboReq);

        PageInfo<SmsTemplateInfoDubboRes> smsTemplateInfoDubboResPageInfo = new PageInfo<>(smsTemplateInfoDubboRes);

        PageSimpleDubboRes<SmsTemplateInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();
        pageSimpleDubboRes.setTotal(smsTemplateInfoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(smsTemplateInfoDubboRes);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(SmsTemplateSaveDubboReq req) {
        // 判断是否存在
        SmsTemplate smsTemplate1 = smsTemplateDao.find(req.getPlatform(), req.getScene(), req.getLang());
        if (smsTemplate1 != null) {
            return null;
        }
        SmsTemplate smsTemplate = new SmsTemplate();
        BeanUtils.copyProperties(req, smsTemplate);
        smsTemplate.setCreateTime(new Date());
        smsTemplate.setModifiedTime(new Date());
        smsTemplateDao.insert(smsTemplate);
        return smsTemplate.getId();
    }

    @Override
    public boolean update(SmsTemplateSaveDubboReq req) {
        // 判断是否存在
        SmsTemplate smsTemplate1 = smsTemplateDao.find(req.getPlatform(), req.getScene(), req.getLang());
        // 当前平台的场景已存在
        if (smsTemplate1 != null && !smsTemplate1.getId().equals(req.getId())) {
            return false;
        }
        SmsTemplate smsTemplate = new SmsTemplate();
        BeanUtils.copyProperties(req, smsTemplate);
        smsTemplate.setModifiedTime(new Date());
        return smsTemplateDao.update(smsTemplate);
    }

    @Override
    public boolean delete(Long id) {
        return smsTemplateDao.delete(id);
    }
}
