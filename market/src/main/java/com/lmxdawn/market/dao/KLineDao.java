package com.lmxdawn.market.dao;

import com.lmxdawn.market.entity.KLine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KLineDao {



    /**
     * 根据uid查询
     * @param uid
     * @return
     */
    KLine findByUid(Long uid);

    /**
     * 根据手机号查询
     * @param tel
     * @return
     */
    KLine findByTel(String tel);

    /**
     * 插入
     * @param member
     * @return
     */
    boolean insertMember(KLine member);

}
