package com.lmxdawn.trade.dao;

import com.lmxdawn.dubboapi.req.trade.UsdtRateQueryDubboReq;
import com.lmxdawn.dubboapi.res.trade.UsdtRateDubboRes;
import com.lmxdawn.trade.entity.UsdtRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsdtRateDao {

    /**
     * 查询
     * @return
     */
    List<UsdtRateDubboRes> listPageDubbo(UsdtRateQueryDubboReq req);

    /**
     * 查询
     * @return
     */
    List<UsdtRate> listAll();

    /**
     * 查询
     * @return
     */
    UsdtRate findByName(@Param("name") String name);

    /**
     * 插入
     * @return
     */
    boolean insert(UsdtRate usdtRate);

    /**
     * 修改
     * @return
     */
    boolean update(UsdtRate usdtRate);

}
