package com.lmxdawn.other.service;

import com.lmxdawn.other.vo.HuaWeiSmsSendVo;
import com.lmxdawn.other.vo.StorageSettingVo;


public interface SettingService {

    /**
     * 上传的
     * @return
     */
    StorageSettingVo listToStorageSettingVo();

    /**
     * 根据模块获取华为云短信的全部配置，返回一个对象
     * @return
     */
    HuaWeiSmsSendVo listToHuaWeiSmsVo();

}
