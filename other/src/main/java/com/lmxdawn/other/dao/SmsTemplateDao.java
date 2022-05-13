package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.SmsTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.SmsTemplateInfoDubboRes;
import com.lmxdawn.other.entity.SmsTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SmsTemplateDao {

    /**
     * 列表
     * @param
     * @return
     */
    List<SmsTemplateInfoDubboRes> listPageDubbo(SmsTemplateQueryDubboReq req);

    /**
     * 查找
     * @param platform
     * @param scene
     * @return
     */
    SmsTemplate find(@Param("platform") Integer platform,
                     @Param("scene") Integer scene,
                     @Param("lang") String lang);

    /**
     * 添加
     * @param
     * @return
     */
    boolean insert(SmsTemplate smsTemplate);

    /**
     * 添加
     * @param
     * @return
     */
    boolean update(SmsTemplate smsTemplate);

    /**
     * 删除
     * @param
     * @return
     */
    boolean delete(Long id);

}
