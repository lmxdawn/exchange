package com.lmxdawn.other.service;

import com.lmxdawn.other.req.RegionListReq;
import com.lmxdawn.other.res.RegionListInfoRes;

import java.util.List;


public interface RegionService {

    List<RegionListInfoRes> listAllByParams(RegionListReq req);

}
