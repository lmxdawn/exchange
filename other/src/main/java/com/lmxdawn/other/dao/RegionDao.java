package com.lmxdawn.other.dao;

import com.lmxdawn.other.entity.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface RegionDao {

    /**
     * 添加
     * @param
     * @return
     */
    List<Region> listAllByParams(@Param("provinceId") Integer provinceId,
                                 @Param("cityId") Integer cityId,
                                 @Param("countyId") Integer countyId);

}
