package com.lmxdawn.trade.dao;

import com.lmxdawn.trade.entity.PairRobot;
import com.lmxdawn.trade.req.PairListPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PairRobotDao {

    /**
     * 查询
     *
     * @return
     */
    List<PairRobot> listAll();

    /**
     * 查询
     *
     * @return
     */
    List<PairRobot> listByPairIdIn(List<Long> ids);

    /**
     * 查询
     *
     * @return
     */
    PairRobot findByTidAndCid(@Param("tradeCoinId") Long tradeCoinId,
                              @Param("coinId") Long coinId);

    /**
     * 插入
     *
     * @return
     */
    boolean insert(PairRobot pairRobot);

    /**
     * 修改
     *
     * @return
     */
    boolean update(PairRobot pairRobot);

}
