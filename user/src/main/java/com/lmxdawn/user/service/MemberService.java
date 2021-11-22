package com.lmxdawn.user.service;

import com.lmxdawn.user.entity.Member;

public interface MemberService {

    Member findByUid(Long uid);
    Member findByTel(String tel);
    boolean insertMember(Member member);
}
