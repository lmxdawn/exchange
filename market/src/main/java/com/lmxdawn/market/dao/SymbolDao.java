package com.lmxdawn.market.dao;

import com.lmxdawn.dubboapi.req.market.SymbolQueryDubboReq;
import com.lmxdawn.dubboapi.res.market.SymbolDubboRes;
import com.lmxdawn.market.entity.Symbol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SymbolDao {

    /**
     * 查询
     * @return
     */
    List<SymbolDubboRes> listPageDubbo(SymbolQueryDubboReq req);

    /**
     * 查询
     * @return
     */
    List<Symbol> listAll();

    /**
     * 查询
     * @return
     */
    Symbol findByTidAndCid(@Param("tradeCoinId") Long tradeCoinId,
                           @Param("coinId") Long coinId);

    /**
     * 插入
     * @return
     */
    boolean insert(Symbol symbol);

    /**
     * 修改
     * @return
     */
    boolean update(Symbol symbol);

}
