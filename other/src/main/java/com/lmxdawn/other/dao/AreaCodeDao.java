package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.AreaCodeQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.AreaCodeInfoDubboRes;
import com.lmxdawn.other.entity.AreaCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AreaCodeDao {

    List<AreaCodeInfoDubboRes> listPageDubbo(AreaCodeQueryDubboReq req);

    List<AreaCode> listPageByLang(@Param("lang") String lang,
                                  @Param("offset") Integer offset,
                                  @Param("limit") Integer limit);

    /**
     * 插入
     *
     * @param
     * @return
     */
    Boolean insert(AreaCode areaCode);

    /**
     * 修改
     *
     * @param
     * @return
     */
    Boolean update(AreaCode areaCode);
}
