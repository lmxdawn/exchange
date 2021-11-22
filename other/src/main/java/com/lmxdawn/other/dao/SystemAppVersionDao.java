package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.SystemAppVersionQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.SystemAppVersionInfoDubboRes;
import com.lmxdawn.other.entity.SystemAppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemAppVersionDao {

    List<SystemAppVersionInfoDubboRes> listPageDubbo(SystemAppVersionQueryDubboReq req);

    /**
     * 查询
     *
     * @param
     * @return
     */
    SystemAppVersion findByVersion(@Param("type") Integer type,
                                   @Param("platform") Integer platform,
                                   @Param("version") String version);

    /**
     * 插入
     *
     * @param
     * @return
     */
    Boolean insert(SystemAppVersion questionHot);

    /**
     * 修改
     *
     * @param
     * @return
     */
    Boolean update(SystemAppVersion questionHot);
}
