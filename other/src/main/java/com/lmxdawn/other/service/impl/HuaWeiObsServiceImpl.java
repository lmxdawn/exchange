package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.res.HuaWeiObsPostParamRes;
import com.lmxdawn.other.res.HuaWeiObsPutParamRes;
import com.lmxdawn.other.service.HuaWeiObsService;
import com.lmxdawn.other.service.SettingService;
import com.lmxdawn.other.util.UUIDStringUtils;
import com.lmxdawn.other.vo.StorageSettingVo;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HuaWeiObsServiceImpl implements HuaWeiObsService {

    @Autowired
    private SettingService settingService;


    @Override
    public HuaWeiObsPostParamRes createUploadPostParam(String suffix) {

        StorageSettingVo storageSettingVo = settingService.listToStorageSettingVo();

        if (storageSettingVo == null) {
            return null;
        }

        String accessKey = storageSettingVo.getAccessKey();
        String secretKey = storageSettingVo.getSecretKey();
        String endPoint = storageSettingVo.getUploadUrl();
        String bucketName = storageSettingVo.getBucket();
        String objectKey = UUIDStringUtils.randomUUID() + "." + suffix;
        // URL有效期，3600秒
        long expireSeconds = 3600L;

        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(endPoint);
        config.setAuthType(AuthTypeEnum.OBS);
        ObsClient obsClient = new ObsClient(accessKey, secretKey, config);

        Map<String, Object> formParams = new HashMap<>();
        String xObsAcl = "public-read";
        PostSignatureRequest request = new PostSignatureRequest();
        request.setExpires(expireSeconds);
        formParams.put("x-obs-acl", xObsAcl);
        request.setFormParams(formParams);
        PostSignatureResponse response = obsClient.createPostSignature(request);

        HuaWeiObsPostParamRes huaWeiObsPostParamRes = new HuaWeiObsPostParamRes();
        huaWeiObsPostParamRes.setKey(objectKey);
        huaWeiObsPostParamRes.setXObsAcl(xObsAcl);
        huaWeiObsPostParamRes.setAccessKeyId(accessKey);
        huaWeiObsPostParamRes.setPolicy(response.getPolicy());
        huaWeiObsPostParamRes.setSignature(response.getSignature());
        String postUrl = "https://" + bucketName + "." + endPoint;
        huaWeiObsPostParamRes.setUrl(postUrl);

        return huaWeiObsPostParamRes;
    }

    @Override
    public List<HuaWeiObsPostParamRes> createUploadPostParamAll(List<String> suffixs) {

        StorageSettingVo storageSettingVo = settingService.listToStorageSettingVo();

        if (storageSettingVo == null) {
            return null;
        }
        String accessKey = storageSettingVo.getAccessKey();
        String secretKey = storageSettingVo.getSecretKey();
        String endPoint = storageSettingVo.getUploadUrl();
        String bucketName = storageSettingVo.getBucket();
        // URL有效期，3600秒
        long expireSeconds = 3600L;

        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(endPoint);
        config.setAuthType(AuthTypeEnum.OBS);

        ObsClient obsClient = new ObsClient(accessKey, secretKey, config);

        Map<String, Object> formParams = new HashMap<>();
        String xObsAcl = "public-read";
        formParams.put("x-obs-acl", xObsAcl);

        List<HuaWeiObsPostParamRes> list = new ArrayList<>();
        for (String suffix : suffixs) {
            String objectKey = UUIDStringUtils.randomUUID() + "." + suffix;
            PostSignatureRequest request = new PostSignatureRequest();
            request.setExpires(expireSeconds);
            request.setFormParams(formParams);
            PostSignatureResponse response = obsClient.createPostSignature(request);
            HuaWeiObsPostParamRes huaWeiObsPostParamRes = new HuaWeiObsPostParamRes();
            huaWeiObsPostParamRes.setKey(objectKey);
            huaWeiObsPostParamRes.setXObsAcl(xObsAcl);
            huaWeiObsPostParamRes.setAccessKeyId(accessKey);
            huaWeiObsPostParamRes.setPolicy(response.getPolicy());
            huaWeiObsPostParamRes.setSignature(response.getSignature());
            String postUrl = "https://" + bucketName + "." + endPoint;
            huaWeiObsPostParamRes.setUrl(postUrl);
            list.add(huaWeiObsPostParamRes);
        }

        return list;
    }


    @Override
    public HuaWeiObsPutParamRes createUploadPutParam(String suffix) {

        StorageSettingVo storageSettingVo = settingService.listToStorageSettingVo();

        if (storageSettingVo == null) {
            return null;
        }

        String accessKey = storageSettingVo.getAccessKey();
        String secretKey = storageSettingVo.getSecretKey();
        String endPoint = storageSettingVo.getUploadUrl();
        String bucketName = storageSettingVo.getBucket();
        String objectKey = UUIDStringUtils.randomUUID() + "." + suffix;
        // URL有效期，3600秒
        long expireSeconds = 3600L;


        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(accessKey, secretKey, endPoint);

        // 替换成您对应的操作
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);

        // 替换为请求本次操作访问的桶名和对象名
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        HuaWeiObsPutParamRes huaWeiObsPutParamRes = new HuaWeiObsPutParamRes();
        huaWeiObsPutParamRes.setSignedUrl(response.getSignedUrl());

        return huaWeiObsPutParamRes;
    }

    @Override
    public List<HuaWeiObsPutParamRes> createUploadPutParamAll(List<String> suffixs) {
        StorageSettingVo storageSettingVo = settingService.listToStorageSettingVo();

        if (storageSettingVo == null) {
            return null;
        }

        String accessKey = storageSettingVo.getAccessKey();
        String secretKey = storageSettingVo.getSecretKey();
        String endPoint = storageSettingVo.getUploadUrl();
        String bucketName = storageSettingVo.getBucket();
        // URL有效期，3600秒
        long expireSeconds = 3600L;

        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(accessKey, secretKey, endPoint);

        List<HuaWeiObsPutParamRes> list = new ArrayList<>();
        for (String suffix : suffixs) {
            String objectKey = UUIDStringUtils.randomUUID() + "." + suffix;
            // 替换成您对应的操作
            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);

            // 替换为请求本次操作访问的桶名和对象名
            request.setBucketName(bucketName);
            request.setObjectKey(objectKey);
            TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
            HuaWeiObsPutParamRes huaWeiObsPutParamRes = new HuaWeiObsPutParamRes();
            huaWeiObsPutParamRes.setSignedUrl(response.getSignedUrl());
            list.add(huaWeiObsPutParamRes);
        }

        return list;
    }
}
