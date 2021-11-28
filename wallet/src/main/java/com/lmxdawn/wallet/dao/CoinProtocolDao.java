package com.lmxdawn.wallet.dao;

import com.lmxdawn.dubboapi.req.wallet.CoinProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolDubboRes;
import com.lmxdawn.wallet.entity.CoinProtocol;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoinProtocolDao {

    List<CoinProtocolDubboRes> listPageDubbo(CoinProtocolQueryDubboReq req);

    /**
     * 获取列表
     *
     * @param
     * @return
     */
    List<CoinProtocol> listAll();

    /**
     * 查询
     *
     * @param
     * @return
     */
    CoinProtocol findById(Long id);

    /**
     * 查询
     *
     * @param
     * @return
     */
    CoinProtocol findByName(String name);

    /**
     * 插入
     *
     * @param
     * @return
     */
    boolean insert(CoinProtocol coinProtocol);

    /**
     * 修改
     *
     * @param
     * @return
     */
    boolean update(CoinProtocol coinProtocol);

}
