package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.RegionDao;
import com.lmxdawn.other.entity.Region;
import com.lmxdawn.other.req.RegionListReq;
import com.lmxdawn.other.res.RegionListInfoRes;
import com.lmxdawn.other.service.RegionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;

    @Override
    public List<RegionListInfoRes> listAllByParams(RegionListReq req) {
        List<Region> regions = regionDao.listAllByParams(req.getProvinceId(), req.getCityId(), req.getCountyId());
        List<RegionListInfoRes> collect = regions.stream().map(v -> {
            RegionListInfoRes infoRes = new RegionListInfoRes();
            BeanUtils.copyProperties(v, infoRes);
            return infoRes;
        }).collect(Collectors.toList());
        return collect;
    }
}
