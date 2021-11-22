package com.lmxdawn.other.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.other.constant.SettingKeyConstant;
import com.lmxdawn.other.dao.SettingDao;
import com.lmxdawn.other.entity.Setting;
import com.lmxdawn.other.res.SettingAppInfoRes;
import com.lmxdawn.other.service.AppSettingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppSettingServiceImpl implements AppSettingService {

    @Autowired
    private SettingDao settingDao;

    @Override
    public SettingAppInfoRes appSettingInfo() {

        List<Setting> settings = settingDao.listByModule(SettingKeyConstant.APP_MODULE);
        if (settings == null || settings.size() == 0) {
            return null;
        }

        List<Setting> settingList = new ArrayList<>();
        for (Setting item : settings) {
            if (!StringUtils.isBlank(item.getKey()) && !StringUtils.isBlank(item.getValue())) {
                switch (item.getKey()){
                    case SettingKeyConstant.APP_IOS_DOWNLOAD_URL:
                    case SettingKeyConstant.APP_ANDROID_DOWNLOAD_URL:
                    case SettingKeyConstant.APP_H5_URL:
                        settingList.add(item);
                }
            }
        }

        if (settingList.size() <= 0) {
            return null;
        }

        Map<String, String> collect = settingList.stream().collect(Collectors.toMap(Setting::getKey, Setting::getValue));
        SettingAppInfoRes settingAppInfoRes = JSON.parseObject(JSON.toJSONString(collect), SettingAppInfoRes.class);

        return settingAppInfoRes;
    }
}
