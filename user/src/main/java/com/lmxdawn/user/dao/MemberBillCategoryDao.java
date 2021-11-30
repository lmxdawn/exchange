package com.lmxdawn.user.dao;

import com.lmxdawn.user.entity.MemberBillCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberBillCategoryDao {

    /**
     * 获取所有
     *
     * @param
     * @return
     */
    List<MemberBillCategory> listSimpleAll();

    /**
     * 获取列表
     *
     * @param
     * @return
     */
    List<MemberBillCategory> listSimpleByNameIn(List<String> names);

    /**
     * 获取
     *
     * @param
     * @return
     */
    MemberBillCategory findByName(String name);

}
