package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.QuestionHotDao;
import com.lmxdawn.other.entity.QuestionHot;
import com.lmxdawn.other.req.QuestionHotListPageReq;
import com.lmxdawn.other.res.QuestionHotListInfoRes;
import com.lmxdawn.other.service.QuestionHotService;
import com.lmxdawn.other.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionHotServiceImpl implements QuestionHotService {

    @Resource
    private QuestionHotDao questionHotDao;

    @Override
    public List<QuestionHotListInfoRes> listPage(QuestionHotListPageReq req) {

        Integer offset = PageUtils.createOffset(req.getPage(), req.getLimit());

        List<QuestionHot> list = questionHotDao.listPage(offset, req.getLimit());

        if (list.size() == 0) {
            return new ArrayList<>();
        }

        List<QuestionHotListInfoRes> collect = list.stream().map(v -> {
            QuestionHotListInfoRes infoRes = new QuestionHotListInfoRes();
            BeanUtils.copyProperties(v, infoRes);
            return infoRes;
        }).collect(Collectors.toList());

        return collect;
    }
}
