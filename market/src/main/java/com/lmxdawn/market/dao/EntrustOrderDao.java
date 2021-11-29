package com.lmxdawn.market.dao;

import com.lmxdawn.market.entity.EntrustOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntrustOrderDao {

    /**
     * 查询
     * @return
     */
    List<EntrustOrder> listPageByMemberId(Long memberId);

    /**
     * 插入
     * @return
     */
    boolean insert(EntrustOrder entrustOrder);

    /**
     * 修改
     * @return
     */
    boolean update(EntrustOrder entrustOrder);

}
