package com.lmxdawn.user.dao;

import com.lmxdawn.user.entity.MemberCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberCoinDao {

    /**
     * 查询
     * @return
     */
    List<MemberCoin> listAllByMemberId(Long memberId);

    /**
     * 查询
     * @return
     */
    List<MemberCoin> listByMemberIdCoinIds(@Param("memberId") Long memberId,
                                           @Param("coinIds") List<Long> coinIds);

    /**
     * 查询
     * @return
     */
    MemberCoin findByMemberIdAndCoinId(@Param("memberId") Long memberId,
                                       @Param("coinId") Long coinId);

    /**
     * 插入
     * @return
     */
    boolean insert(MemberCoin memberCoin);

    /**
     * 批量插入（如果存在则更新）
     * @return
     */
    boolean insertBatch(List<MemberCoin> memberCoinList);

    /**
     * 更新
     * @return
     */
    boolean update(MemberCoin memberCoin);

    /**
     * 增加余额
     * @return
     */
    boolean incrBalance(MemberCoin memberCoin);

    /**
     * 减少余额
     * @return
     */
    boolean decrBalance(MemberCoin memberCoin);

    /**
     * 冻结余额
     * @return
     */
    boolean frozen(MemberCoin memberCoin);

    /**
     * 解冻余额
     * @return
     */
    boolean unfrozen(MemberCoin memberCoin);

}
