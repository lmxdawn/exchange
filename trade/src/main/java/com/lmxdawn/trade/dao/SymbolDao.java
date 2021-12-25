package com.lmxdawn.trade.dao;

import com.lmxdawn.dubboapi.req.trade.SymbolQueryDubboReq;
import com.lmxdawn.dubboapi.res.trade.SymbolDubboRes;
import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.req.SymbolListPageReq;
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
    List<Symbol> listPage(SymbolListPageReq req);

    /**
     * 查询
     * @return
     */
    List<Symbol> listByIdIn(List<Long> ids);

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
