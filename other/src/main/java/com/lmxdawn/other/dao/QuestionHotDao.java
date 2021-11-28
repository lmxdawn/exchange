package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.QuestionHotQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.QuestionHotInfoDubboRes;
import com.lmxdawn.other.entity.QuestionHot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionHotDao {

    List<QuestionHotInfoDubboRes> listPageDubbo(QuestionHotQueryDubboReq req);

    /**
     * 获取列表
     *
     * @param
     * @return
     */
    List<QuestionHot> listPage(@Param("offset") Integer offset,
                               @Param("limit") Integer limit);

    /**
     * 查询
     *
     * @param
     * @return
     */
    QuestionHot findById(Long id);

    /**
     * 插入
     *
     * @param
     * @return
     */
    Boolean insert(QuestionHot questionHot);

    /**
     * 修改
     *
     * @param
     * @return
     */
    Boolean update(QuestionHot questionHot);
}
