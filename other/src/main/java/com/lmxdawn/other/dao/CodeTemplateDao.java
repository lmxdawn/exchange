package com.lmxdawn.other.dao;

import com.lmxdawn.dubboapi.req.other.CodeTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.res.other.CodeTemplateInfoDubboRes;
import com.lmxdawn.other.entity.CodeTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CodeTemplateDao {

    /**
     * 列表
     * @param
     * @return
     */
    List<CodeTemplateInfoDubboRes> listPageDubbo(CodeTemplateQueryDubboReq req);

    /**
     * 查找
     * @param platform
     * @param scene
     * @return
     */
    CodeTemplate find(@Param("platform") Integer platform,
                      @Param("scene") Integer scene,
                      @Param("lang") String lang);

    /**
     * 添加
     * @param
     * @return
     */
    boolean insert(CodeTemplate codeTemplate);

    /**
     * 添加
     * @param
     * @return
     */
    boolean update(CodeTemplate codeTemplate);

    /**
     * 删除
     * @param
     * @return
     */
    boolean delete(Long id);

}
