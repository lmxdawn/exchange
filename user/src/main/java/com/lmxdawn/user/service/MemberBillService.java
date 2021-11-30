package com.lmxdawn.user.service;

import com.lmxdawn.user.req.MemberBillListPageReq;
import com.lmxdawn.user.res.MemberBillRes;

import java.util.List;

public interface MemberBillService {

    List<MemberBillRes> listPage(MemberBillListPageReq req);

}
