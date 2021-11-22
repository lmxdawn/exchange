package com.lmxdawn.other.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.ProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.ProtocolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.ProtocolInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.ProtocolDubboService;
import com.lmxdawn.other.dao.ProtocolDao;
import com.lmxdawn.other.entity.Protocol;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 协议
 */
@DubboService
public class ProtocolDubboServiceImpl implements ProtocolDubboService {

    @Resource
    private ProtocolDao protocolDao;

    @Override
    public PageSimpleDubboRes<ProtocolInfoDubboRes> list(ProtocolQueryDubboReq req) {
        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<ProtocolInfoDubboRes> infoDubboResList = protocolDao.listPageDubbo(req);

        PageSimpleDubboRes<ProtocolInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList == null || infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<ProtocolInfoDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(infoDubboResList);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(ProtocolSaveDubboReq req) {
        Protocol byKey = protocolDao.findByKey(req.getKey());
        if (byKey != null) {
            return null;
        }
        Protocol protocol = new Protocol();
        BeanUtils.copyProperties(req, protocol);
        protocol.setCreateTime(new Date());
        protocol.setModifiedTime(new Date());
        protocolDao.insert(protocol);
        return protocol.getId();
    }

    @Override
    public boolean update(ProtocolSaveDubboReq req) {
        Protocol byKey = protocolDao.findByKey(req.getKey());
        if (byKey != null && !byKey.getId().equals(req.getId())) {
            return false;
        }
        Protocol protocol = new Protocol();
        BeanUtils.copyProperties(req, protocol);
        protocol.setModifiedTime(new Date());
        return protocolDao.update(protocol);
    }
}
