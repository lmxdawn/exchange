package com.lmxdawn.wallet.dao;

import com.lmxdawn.dubboapi.req.wallet.CoinQueryDubboReq;
import com.lmxdawn.dubboapi.res.wallet.CoinDubboRes;
import com.lmxdawn.wallet.entity.Coin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoinDao {

    List<CoinDubboRes> listPageDubbo(CoinQueryDubboReq req);

    /**
     * 获取列表
     *
     * @param
     * @return
     */
    List<Coin> listAll();

    /**
     * 获取列表
     *
     * @param
     * @return
     */
    List<Coin> listByIdIn(List<Long> ids);

    /**
     * 查询
     *
     * @param
     * @return
     */
    Coin findById(Long id);

    /**
     * 查询
     *
     * @param
     * @return
     */
    Coin findByName(String name);

    /**
     * 插入
     *
     * @param
     * @return
     */
    boolean insert(Coin coin);

    /**
     * 修改
     *
     * @param
     * @return
     */
    boolean update(Coin coin);

}
