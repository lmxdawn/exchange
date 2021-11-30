package com.lmxdawn.user.service;

import com.lmxdawn.user.entity.Member;

public interface MemberService {

    Member findByMemberId(Long uid);
    Member findByTel(String tel);
    boolean create(Member member);
}
