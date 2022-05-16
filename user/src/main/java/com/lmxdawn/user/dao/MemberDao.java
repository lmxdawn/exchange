package com.lmxdawn.user.dao;

import com.lmxdawn.user.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    /**
     * 查询所有
     * @return
     */
    List<Member> listAll();

    /**
     * 查询
     * @return
     */
    Member findByMemberId(Long memberId);

    /**
     * 查询各种安全相关
     * @return
     */
    Member findPwdByMemberId(Long memberId);

    /**
     * 根据手机号查询
     * @param tel
     * @return
     */
    Member findByTel(String tel);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    Member findByEmail(String email);

    /**
     * 插入
     * @param member
     * @return
     */
    boolean insert(Member member);

    /**
     * 修改
     * @param member
     * @return
     */
    boolean update(Member member);

}
