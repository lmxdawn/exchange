package com.lmxdawn.user.service.impl;

import com.lmxdawn.user.dao.MemberDao;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Override
    public Member findByMemberId(Long memberId) {
        return memberDao.findByMemberId(memberId);
    }

    @Override
    public Member findByTel(String tel) {
        return memberDao.findByTel(tel);
    }

    @Override
    public boolean create(Member member) {
        member.setCreateTime(new Date());
        member.setModifiedTime(new Date());
        return memberDao.insert(member);
    }
}
