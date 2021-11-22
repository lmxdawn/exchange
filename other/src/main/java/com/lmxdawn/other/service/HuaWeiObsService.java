package com.lmxdawn.other.service;

import com.lmxdawn.other.res.HuaWeiObsPostParamRes;
import com.lmxdawn.other.res.HuaWeiObsPutParamRes;

import java.util.List;


public interface HuaWeiObsService {

    /**
     * 获取华为云存储的上传参数
     * @return
     */
    HuaWeiObsPostParamRes createUploadPostParam(String suffix);

    /**
     * 获取华为云存储的上传参数
     * @return
     */
    List<HuaWeiObsPostParamRes> createUploadPostParamAll(List<String> suffixs);

    /**
     * 获取华为云存储的上传参数
     * @return
     */
    HuaWeiObsPutParamRes createUploadPutParam(String suffix);

    /**
     * 获取华为云存储的上传参数
     * @return
     */
    List<HuaWeiObsPutParamRes> createUploadPutParamAll(List<String> suffixs);

}
