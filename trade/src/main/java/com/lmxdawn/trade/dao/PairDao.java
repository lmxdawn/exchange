package com.lmxdawn.trade.dao;

import com.lmxdawn.dubboapi.req.trade.PairQueryDubboReq;
import com.lmxdawn.dubboapi.res.trade.PairDubboRes;
import com.lmxdawn.trade.entity.Pair;
import com.lmxdawn.trade.req.PairListPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PairDao {

    /**
     * 查询
     * @return
     */
    List<PairDubboRes> listPageDubbo(PairQueryDubboReq req);

    /**
     * 查询
     * @return
     */
    List<Pair> listPage(PairListPageReq req);

    /**
     * 查询
     * @return
     */
    List<Pair> listByIdIn(List<Long> ids);

    /**
     * 查询
     * @return
     */
    Pair findByTidAndCid(@Param("tradeCoinId") Long tradeCoinId,
                         @Param("coinId") Long coinId);

    /**
     * 插入
     * @return
     */
    boolean insert(Pair pair);

    /**
     * 修改
     * @return
     */
    boolean update(Pair pair);

}
