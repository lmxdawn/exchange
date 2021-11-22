package com.lmxdawn.other.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.QuestionHotQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.QuestionHotSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.QuestionHotInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.QuestionHotDubboService;
import com.lmxdawn.other.dao.QuestionHotDao;
import com.lmxdawn.other.entity.QuestionHot;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 热门问题
 */
@DubboService
public class QuestionHotDubboServiceImpl implements QuestionHotDubboService {

    @Resource
    private QuestionHotDao questionHotDao;

    @Override
    public PageSimpleDubboRes<QuestionHotInfoDubboRes> list(QuestionHotQueryDubboReq req) {
        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<QuestionHotInfoDubboRes> infoDubboResList = questionHotDao.listPageDubbo(req);

        PageSimpleDubboRes<QuestionHotInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<QuestionHotInfoDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(QuestionHotSaveDubboReq req) {
        QuestionHot questionHot = new QuestionHot();
        BeanUtils.copyProperties(req, questionHot);
        questionHot.setCreateTime(new Date());
        questionHot.setModifiedTime(new Date());
        questionHotDao.insert(questionHot);
        return questionHot.getId();
    }

    @Override
    public boolean update(QuestionHotSaveDubboReq req) {
        QuestionHot questionHot = new QuestionHot();
        BeanUtils.copyProperties(req, questionHot);
        questionHot.setModifiedTime(new Date());
        return questionHotDao.update(questionHot);
    }
}
