package com.lmxdawn.user.dao;

import com.lmxdawn.user.entity.MemberBill;
import com.lmxdawn.user.req.MemberBillListPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberBillDao {

    /**
     * 获取列表
     *
     * @param
     * @return
     */
    List<MemberBill> listPage(MemberBillListPageReq req);

    /**
     * 插入
     *
     * @param
     * @return
     */
    Boolean insert(MemberBill memberBill);

    /**
     * 批量插入
     *
     * @param
     * @return
     */
    Boolean insertBatch(List<MemberBill> list);

}
