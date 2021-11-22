package com.lmxdawn.example.dao;

import com.lmxdawn.example.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    /**
     * 根据uid查询
     * @param uid
     * @return
     */
    Member findByUid(Long uid);

    /**
     * 根据手机号查询
     * @param tel
     * @return
     */
    Member findByTel(String tel);

    /**
     * 插入
     * @param member
     * @return
     */
    boolean insertMember(Member member);

}
