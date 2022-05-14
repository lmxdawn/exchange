package com.lmxdawn.other.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.other.constant.SettingKeyConstant;
import com.lmxdawn.other.dao.SettingDao;
import com.lmxdawn.other.entity.Setting;
import com.lmxdawn.other.service.SettingService;
import com.lmxdawn.other.vo.EmailSendVo;
import com.lmxdawn.other.vo.HuaWeiSmsSendVo;
import com.lmxdawn.other.vo.StorageSettingVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingDao settingDao;

    @Override
    public StorageSettingVo listToStorageSettingVo() {
        List<Setting> settings = settingDao.listByModule(SettingKeyConstant.STORAGE_MODULE);
        if (settings == null || settings.size() == 0) {
            return new StorageSettingVo();
        }

        List<Setting> settingList = new ArrayList<>();
        for (Setting item : settings) {
            if (!StringUtils.isBlank(item.getKey()) && !StringUtils.isBlank(item.getValue())) {
                switch (item.getKey()){
                    case SettingKeyConstant.STORAGE_DOMAIN:
                    case SettingKeyConstant.STORAGE_UPLOAD_URL:
                    case SettingKeyConstant.STORAGE_ACCESS_KEY:
                    case SettingKeyConstant.STORAGE_SECRET_KEY:
                    case SettingKeyConstant.STORAGE_BUCKET:
                        settingList.add(item);
                }
            }
        }

        if (settingList.size() <= 0) {
            return null;
        }

        Map<String, String> collect = settingList.stream().collect(Collectors.toMap(Setting::getKey, Setting::getValue));
        return JSON.parseObject(JSON.toJSONString(collect), StorageSettingVo.class);
    }

    @Override
    public HuaWeiSmsSendVo listToHuaWeiSmsVo() {

        List<Setting> settings = settingDao.listByModule(SettingKeyConstant.HUA_WEI_SMS_MODULE);

        if (settings == null) {
            return new HuaWeiSmsSendVo();
        }

        List<Setting> settingList = new ArrayList<>();
        for (Setting item : settings) {
            if (!StringUtils.isBlank(item.getKey()) && !StringUtils.isBlank(item.getValue())) {
                switch (item.getKey()){
                    case SettingKeyConstant.HUA_WEI_SMS_ACCESS_KEY:
                    case SettingKeyConstant.HUA_WEI_SMS_APP_SECRET:
                    case SettingKeyConstant.HUA_WEI_SMS_SEND_URL:
                    case SettingKeyConstant.HUA_WEI_SMS_SENDER:
                    case SettingKeyConstant.HUA_WEI_SMS_SIGNATURE:
                        settingList.add(item);
                }
            }
        }

        if (settingList.size() <= 0) {
            return null;
        }

        Map<String, String> collect = settingList.stream().collect(Collectors.toMap(Setting::getKey, Setting::getValue));
        return JSON.parseObject(JSON.toJSONString(collect), HuaWeiSmsSendVo.class);
    }

    @Override
    public EmailSendVo listToEmailSendVo() {
        List<Setting> settings = settingDao.listByModule(SettingKeyConstant.EMAIL);

        if (settings == null) {
            return new EmailSendVo();
        }

        List<Setting> settingList = new ArrayList<>();
        for (Setting item : settings) {
            if (!StringUtils.isBlank(item.getKey()) && !StringUtils.isBlank(item.getValue())) {
                switch (item.getKey()){
                    case SettingKeyConstant.EMAIL_HOST:
                    case SettingKeyConstant.EMAIL_USERNAME:
                    case SettingKeyConstant.EMAIL_PASSWORD:
                        settingList.add(item);
                }
            }
        }

        if (settingList.size() <= 0) {
            return null;
        }

        Map<String, String> collect = settingList.stream().collect(Collectors.toMap(Setting::getKey, Setting::getValue));
        return JSON.parseObject(JSON.toJSONString(collect), EmailSendVo.class);
    }
}
