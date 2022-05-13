package com.lmxdawn.other.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.AreaCodeQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.AreaCodeSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.AreaCodeInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.AreaCodeDubboService;
import com.lmxdawn.other.dao.AreaCodeDao;
import com.lmxdawn.other.entity.AreaCode;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 电话地区
 */
@DubboService
public class AreaCodeDubboServiceImpl implements AreaCodeDubboService {

    @Resource
    private AreaCodeDao areaCodeDao;

    @Override
    public PageSimpleDubboRes<AreaCodeInfoDubboRes> list(AreaCodeQueryDubboReq req) {
        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<AreaCodeInfoDubboRes> infoDubboResList = areaCodeDao.listPageDubbo(req);

        PageSimpleDubboRes<AreaCodeInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<AreaCodeInfoDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(AreaCodeSaveDubboReq req) {
        AreaCode areaCode = new AreaCode();
        BeanUtils.copyProperties(req, areaCode);
        areaCode.setCreateTime(new Date());
        areaCode.setModifiedTime(new Date());
        areaCodeDao.insert(areaCode);
        return areaCode.getId();
    }

    @Override
    public boolean update(AreaCodeSaveDubboReq req) {
        AreaCode areaCode = new AreaCode();
        BeanUtils.copyProperties(req, areaCode);
        areaCode.setModifiedTime(new Date());
        return areaCodeDao.update(areaCode);
    }
}
