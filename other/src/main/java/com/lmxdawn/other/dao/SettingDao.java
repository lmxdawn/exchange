package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.SettingQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.SettingInfoDubboRes;
import com.lmxdawn.other.entity.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SettingDao {

    /**
     * 列表
     * @param
     * @return
     */
    List<Setting> listByModule(@Param("module") Integer module);


    /**
     * 列表
     * @param
     * @return
     */
    List<SettingInfoDubboRes> listPageDubbo(SettingQueryDubboReq req);

    /**
     * 查询
     * @param
     * @return
     */
    Setting findByKeyDubbo(SettingQueryDubboReq req);

    /**
     * 更新数据
     * @param setting
     * @return
     */
    boolean updateSettingDubbo(Setting setting);

}
