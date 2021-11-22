package com.lmxdawn.other.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.SystemAppVersionQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SystemAppVersionSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SystemAppVersionInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.SystemAppVersionDubboService;
import com.lmxdawn.other.dao.SystemAppVersionDao;
import com.lmxdawn.other.entity.SystemAppVersion;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * APP版本
 */
@DubboService
public class SystemAppVersionDubboServiceImpl implements SystemAppVersionDubboService {

    @Resource
    private SystemAppVersionDao systemAppVersionDao;

    @Override
    public PageSimpleDubboRes<SystemAppVersionInfoDubboRes> list(SystemAppVersionQueryDubboReq req) {
        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<SystemAppVersionInfoDubboRes> infoDubboResList = systemAppVersionDao.listPageDubbo(req);

        PageSimpleDubboRes<SystemAppVersionInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<SystemAppVersionInfoDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(SystemAppVersionSaveDubboReq req) {
        SystemAppVersion systemAppVersion = new SystemAppVersion();
        BeanUtils.copyProperties(req, systemAppVersion);
        systemAppVersion.setCreateTime(new Date());
        systemAppVersion.setModifiedTime(new Date());
        systemAppVersionDao.insert(systemAppVersion);
        return systemAppVersion.getId();
    }

    @Override
    public boolean update(SystemAppVersionSaveDubboReq req) {
        SystemAppVersion systemAppVersion = new SystemAppVersion();
        BeanUtils.copyProperties(req, systemAppVersion);
        systemAppVersion.setModifiedTime(new Date());
        return systemAppVersionDao.update(systemAppVersion);
    }
}
