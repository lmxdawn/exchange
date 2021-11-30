package com.lmxdawn.user.service.impl;

import com.lmxdawn.user.dao.MemberBillCategoryDao;
import com.lmxdawn.user.entity.MemberBillCategory;
import com.lmxdawn.user.res.MemberBillCategoryRes;
import com.lmxdawn.user.service.MemberBillCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberBillCategoryServiceImpl implements MemberBillCategoryService {

    @Autowired
    private MemberBillCategoryDao memberBillCategoryDao;

    @Override
    public List<MemberBillCategoryRes> listAll() {

        List<MemberBillCategory> memberBillCategories = memberBillCategoryDao.listSimpleAll();

        return memberBillCategories.stream().map(v -> {
            MemberBillCategoryRes res = new MemberBillCategoryRes();
            BeanUtils.copyProperties(v, res);
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> mapByNameIn(List<String> names) {

        List<MemberBillCategory> memberBillCategories = memberBillCategoryDao.listSimpleByNameIn(names);
        Map<String, String> map = new HashMap<>();

        if (memberBillCategories.size() == 0) {
            return map;
        }

        memberBillCategories.forEach(v -> {
            map.put(v.getName(), v.getTitle());
        });

        return map;
    }

    @Override
    public MemberBillCategory findByName(String name) {
        return memberBillCategoryDao.findByName(name);
    }
}
