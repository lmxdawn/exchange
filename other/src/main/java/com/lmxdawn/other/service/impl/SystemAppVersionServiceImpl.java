package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.SystemAppVersionDao;
import com.lmxdawn.other.entity.SystemAppVersion;
import com.lmxdawn.other.req.SystemAppVersionReq;
import com.lmxdawn.other.res.SystemAppVersionInfoRes;
import com.lmxdawn.other.service.SystemAppVersionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemAppVersionServiceImpl implements SystemAppVersionService {

    @Resource
    private SystemAppVersionDao systemAppVersionDao;


    @Override
    public SystemAppVersionInfoRes update(SystemAppVersionReq req) {

        // 最新的全量按照包
        SystemAppVersion systemAppVersion = systemAppVersionDao.findByVersion(1, req.getPlatform(), req.getVersion());
        if (systemAppVersion == null) {
            systemAppVersion = systemAppVersionDao.findByVersion(2, req.getPlatform(), req.getVersion());
        }

        SystemAppVersionInfoRes systemAppVersionInfoRes = new SystemAppVersionInfoRes();
        if (systemAppVersion == null) {
            return systemAppVersionInfoRes;
        }

        if (Integer.parseInt(systemAppVersion.getVersion().replace(".", "")) > Integer.parseInt(req.getVersion().replace(".", ""))) {
            BeanUtils.copyProperties(systemAppVersion, systemAppVersionInfoRes);
            systemAppVersionInfoRes.setIsUpdate(1);
        }

        return systemAppVersionInfoRes;
    }
}
