package com.lmxdawn.trade.dao;

import com.lmxdawn.trade.entity.EntrustOrderDetail;
import com.lmxdawn.trade.req.EntrustOrderDetailReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntrustOrderDetailDao {

    /**
     * 查询
     *
     * @return
     */
    List<EntrustOrderDetail> listPageByMemberIdAndOrderId(EntrustOrderDetailReq req);

    /**
     * 插入
     *
     * @return
     */
    boolean insert(EntrustOrderDetail entrustOrderDetail);

    /**
     * 批量插入
     *
     * @return
     */
    boolean insertBatch(List<EntrustOrderDetail> list);

}
