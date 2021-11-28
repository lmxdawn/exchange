package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.ProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.ProtocolInfoDubboRes;
import com.lmxdawn.other.entity.Protocol;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProtocolDao {

    List<ProtocolInfoDubboRes> listPageDubbo(ProtocolQueryDubboReq req);

    /**
     * 查询
     *
     * @param
     * @return
     */
    Protocol findByKey(String key);

    /**
     * 插入
     *
     * @param
     * @return
     */
    Boolean insert(Protocol protocol);

    /**
     * 修改
     *
     * @param
     * @return
     */
    Boolean update(Protocol protocol);
}
