package com.lmxdawn.user.service;

import com.lmxdawn.user.res.MemberCoinRes;

import java.util.List;

public interface MemberCoinService {

    List<MemberCoinRes> listAllByMemberId(Long memberId);

}
