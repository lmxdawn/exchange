package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.ProtocolDao;
import com.lmxdawn.other.entity.Protocol;
import com.lmxdawn.other.res.ProtocolInfoRes;
import com.lmxdawn.other.service.ProtocolService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProtocolServiceImpl implements ProtocolService {

    @Resource
    private ProtocolDao protocolDao;

    @Override
    public ProtocolInfoRes findByKey(String key) {
        ProtocolInfoRes protocolInfoRes = new ProtocolInfoRes();
        Protocol byKey = protocolDao.findByKey(key);
        if (byKey != null) {
            BeanUtils.copyProperties(byKey, protocolInfoRes);
        }
        return protocolInfoRes;
    }
}
