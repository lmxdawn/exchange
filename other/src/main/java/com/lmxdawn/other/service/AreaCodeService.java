package com.lmxdawn.other.service;

import com.lmxdawn.other.req.AreaCodeListPageReq;
import com.lmxdawn.other.res.AreaCodeInfoRes;

import java.util.List;

public interface AreaCodeService {

    List<AreaCodeInfoRes> listByLang(AreaCodeListPageReq req);

}
