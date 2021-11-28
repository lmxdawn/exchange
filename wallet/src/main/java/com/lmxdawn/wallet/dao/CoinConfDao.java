package com.lmxdawn.wallet.dao;

import com.lmxdawn.dubboapi.req.wallet.CoinConfQueryDubboReq;
import com.lmxdawn.dubboapi.res.wallet.CoinConfDubboRes;
import com.lmxdawn.wallet.entity.CoinConf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoinConfDao {

    List<CoinConfDubboRes> listPageDubbo(CoinConfQueryDubboReq req);

    /**
     * 查询
     *
     * @param
     * @return
     */
    CoinConf findById(Long id);

    /**
     * 查询
     *
     * @param
     * @return
     */
    CoinConf findByCoinIdAndProtocolId(@Param("coinId") Long coinId,
                                       @Param("protocolId") Long protocolId);

    /**
     * 插入
     *
     * @param
     * @return
     */
    boolean insert(CoinConf coinConf);

    /**
     * 修改
     *
     * @param
     * @return
     */
    boolean update(CoinConf coinConf);

}
