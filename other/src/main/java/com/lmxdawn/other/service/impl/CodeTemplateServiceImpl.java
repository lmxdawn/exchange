package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.CodeTemplateDao;
import com.lmxdawn.other.entity.CodeTemplate;
import com.lmxdawn.other.service.CodeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeTemplateServiceImpl implements CodeTemplateService {

    @Autowired
    private CodeTemplateDao codeTemplateDao;

    @Override
    public CodeTemplate find(Integer platform, Integer scene, String lang) {
        return codeTemplateDao.find(platform, scene, lang);
    }
}
