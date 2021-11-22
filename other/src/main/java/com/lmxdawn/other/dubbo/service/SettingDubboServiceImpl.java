package com.lmxdawn.other.dubbo.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.other.SettingQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SettingSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SettingInfoDubboRes;
import com.lmxdawn.dubboapi.res.other.StorageSettingVoDubboRes;
import com.lmxdawn.dubboapi.res.other.UploadUrlAndThumbDubboRes;
import com.lmxdawn.dubboapi.service.other.SettingDubboService;
import com.lmxdawn.other.constant.SettingKeyConstant;
import com.lmxdawn.other.dao.SettingDao;
import com.lmxdawn.other.entity.Setting;
import com.lmxdawn.other.util.PublicFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 配置服务
 */
@DubboService
public class SettingDubboServiceImpl implements SettingDubboService {

    @Autowired
    private SettingDao settingDao;

    @Override
    public PageSimpleDubboRes<SettingInfoDubboRes> list(SettingQueryDubboReq settingQueryDubboReq) {
        int offset = (settingQueryDubboReq.getPage() - 1) * settingQueryDubboReq.getLimit();
        PageHelper.offsetPage(offset, settingQueryDubboReq.getLimit());
        List<SettingInfoDubboRes> settingInfoDubboRes = settingDao.listPageDubbo(settingQueryDubboReq);

        PageInfo<SettingInfoDubboRes> settingInfoDubboResPageInfo = new PageInfo<>(settingInfoDubboRes);

        PageSimpleDubboRes<SettingInfoDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();
        pageSimpleDubboRes.setTotal(settingInfoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(settingInfoDubboRes);
        return pageSimpleDubboRes;
    }

    @Override
    public String findByKey(Integer module, String key) {
        SettingQueryDubboReq settingQueryDubboReq = new SettingQueryDubboReq();
        settingQueryDubboReq.setModule(module);
        settingQueryDubboReq.setKey(key);
        Setting byKeyDubbo = settingDao.findByKeyDubbo(settingQueryDubboReq);

        String value = byKeyDubbo.getValue();
        return value;
    }

    @Override
    public Double registerGiveCoin() {
        String byKey = findByKey(SettingKeyConstant.OPERATE_MODULE, SettingKeyConstant.OPERATE_REGISTER_GIVE_COIN);
        return Double.valueOf(byKey);
    }

    @Override
    public Double rewardFeeRate() {
        String byKey = findByKey(SettingKeyConstant.OPERATE_MODULE, SettingKeyConstant.OPERATE_REWARD_FEE_RATE);
        return Double.valueOf(byKey);
    }

    @Override
    public UploadUrlAndThumbDubboRes createUploadUrlAndThumb(String filePath) {
        String domain = findByKey(SettingKeyConstant.STORAGE_MODULE, SettingKeyConstant.STORAGE_DOMAIN);

        String uploadUrl = PublicFileUtils.createUploadUrl(domain, filePath);
        String uploadUrlThumb = PublicFileUtils.createUploadUrlThumb(domain, filePath, PublicFileUtils.STYLE_THUMB);

        UploadUrlAndThumbDubboRes uploadUrlAndThumbDubboRes = new UploadUrlAndThumbDubboRes();
        uploadUrlAndThumbDubboRes.setUrl(uploadUrl);
        uploadUrlAndThumbDubboRes.setThumbUrl(uploadUrlThumb);
        return uploadUrlAndThumbDubboRes;
    }

    @Override
    public StorageSettingVoDubboRes listToStorageSettingVo() {
        List<Setting> settings = settingDao.listByModule(SettingKeyConstant.STORAGE_MODULE);
        if (settings == null || settings.size() == 0) {
            return null;
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
        return JSON.parseObject(JSON.toJSONString(collect), StorageSettingVoDubboRes.class);
    }

    @Override
    public boolean updateSetting(SettingSaveDubboReq settingSaveDubboReq) {
        Setting setting = new Setting();
        setting.setId(settingSaveDubboReq.getId());
        setting.setValue(settingSaveDubboReq.getValue());
        setting.setModifiedTime(new Date());
        return settingDao.updateSettingDubbo(setting);
    }
}
