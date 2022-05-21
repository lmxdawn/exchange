package com.lmxdawn.trade.dao;

import com.lmxdawn.trade.entity.EntrustOrder;
import com.lmxdawn.trade.req.EntrustOrderListPageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntrustOrderDao {

    /**
     * 查询
     * @return
     */
    List<EntrustOrder> listPageByMemberId(EntrustOrderListPageReq req);

    /**
     * 查询
     * @return
     */
    List<EntrustOrder> listByIdIn(List<Long> ids);

    /**
     * 查询
     * @return
     */
    EntrustOrder findById(Long id);

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

    /**
     * 增加
     * @return
     */
    boolean incr(EntrustOrder entrustOrder);

}
