package com.lmxdawn.user.service;

import com.lmxdawn.user.entity.MemberBillCategory;
import com.lmxdawn.user.res.MemberBillCategoryRes;

import java.util.List;
import java.util.Map;

public interface MemberBillCategoryService {

    List<MemberBillCategoryRes> listAll();

    Map<String, String> mapByNameIn(List<String> names);

    MemberBillCategory findByName(String name);

}
