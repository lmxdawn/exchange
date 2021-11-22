package com.lmxdawn.dubboapi.service.other;

import com.lmxdawn.dubboapi.req.other.SettingQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SettingSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SettingInfoDubboRes;
import com.lmxdawn.dubboapi.res.other.StorageSettingVoDubboRes;
import com.lmxdawn.dubboapi.res.other.UploadUrlAndThumbDubboRes;

/**
 * 配置
 */
public interface SettingDubboService {

    PageSimpleDubboRes<SettingInfoDubboRes> list(SettingQueryDubboReq settingQueryDubboReq);

    String findByKey(Integer module, String key);

    Double registerGiveCoin();

    Double rewardFeeRate();

    UploadUrlAndThumbDubboRes createUploadUrlAndThumb(String filePath);

    StorageSettingVoDubboRes listToStorageSettingVo();

    boolean updateSetting(SettingSaveDubboReq settingSaveDubboReq);

}
