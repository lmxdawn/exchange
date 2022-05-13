package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.AreaCodeDao;
import com.lmxdawn.other.entity.AreaCode;
import com.lmxdawn.other.req.AreaCodeListPageReq;
import com.lmxdawn.other.res.AreaCodeInfoRes;
import com.lmxdawn.other.service.AreaCodeService;
import com.lmxdawn.other.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaCodeServiceImpl implements AreaCodeService {

    @Autowired
    private AreaCodeDao areaCodeDao;

    @Override
    public List<AreaCodeInfoRes> listByLang(AreaCodeListPageReq req) {
        Integer offset = PageUtils.createOffset(req.getPage(), req.getLimit());

        List<AreaCode> list = areaCodeDao.listPageByLang(req.getLang(), offset, req.getLimit());
        if (list.size() == 0) {
            return new ArrayList<>();
        }


        List<AreaCodeInfoRes> collect = list.stream().map(v -> {
            AreaCodeInfoRes infoRes = new AreaCodeInfoRes();
            BeanUtils.copyProperties(v, infoRes);
            return infoRes;
        }).collect(Collectors.toList());

        return collect;
    }
}
