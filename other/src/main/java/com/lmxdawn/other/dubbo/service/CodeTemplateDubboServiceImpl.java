package com.lmxdawn.other.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.CodeTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.CodeTemplateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.CodeTemplateInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.CodeTemplateDubboService;
import com.lmxdawn.other.dao.CodeTemplateDao;
import com.lmxdawn.other.entity.CodeTemplate;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 配置服务
 */
@DubboService
public class CodeTemplateDubboServiceImpl implements CodeTemplateDubboService {

    @Autowired
    private CodeTemplateDao codeTemplateDao;

    @Override
    public PageSimpleDubboRes<CodeTemplateInfoDubboRes> list(CodeTemplateQueryDubboReq req) {
        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<CodeTemplateInfoDubboRes> codeTemplateInfoDubboRes = codeTemplateDao.listPageDubbo(req);

        PageInfo<CodeTemplateInfoDubboRes> smsTemplateInfoDubboResPageInfo = new PageInfo<>(codeTemplateInfoDubboRes);

        PageSimpleDubboRes<CodeTemplateInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();
        pageSimpleDubboRes.setTotal(smsTemplateInfoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(codeTemplateInfoDubboRes);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(CodeTemplateSaveDubboReq req) {
        // 判断是否存在
        CodeTemplate codeTemplate1 = codeTemplateDao.find(req.getPlatform(), req.getScene(), req.getLang());
        if (codeTemplate1 != null) {
            return null;
        }
        CodeTemplate codeTemplate = new CodeTemplate();
        BeanUtils.copyProperties(req, codeTemplate);
        codeTemplate.setCreateTime(new Date());
        codeTemplate.setModifiedTime(new Date());
        codeTemplateDao.insert(codeTemplate);
        return codeTemplate.getId();
    }

    @Override
    public boolean update(CodeTemplateSaveDubboReq req) {
        // 判断是否存在
        CodeTemplate codeTemplate1 = codeTemplateDao.find(req.getPlatform(), req.getScene(), req.getLang());
        // 当前平台的场景已存在
        if (codeTemplate1 != null && !codeTemplate1.getId().equals(req.getId())) {
            return false;
        }
        CodeTemplate codeTemplate = new CodeTemplate();
        BeanUtils.copyProperties(req, codeTemplate);
        codeTemplate.setModifiedTime(new Date());
        return codeTemplateDao.update(codeTemplate);
    }

    @Override
    public boolean delete(Long id) {
        return codeTemplateDao.delete(id);
    }
}
