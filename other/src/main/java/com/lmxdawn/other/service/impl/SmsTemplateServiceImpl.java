package com.lmxdawn.other.service.impl;

import com.lmxdawn.other.dao.SmsTemplateDao;
import com.lmxdawn.other.entity.SmsTemplate;
import com.lmxdawn.other.service.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateDao smsTemplateDao;

    @Override
    public SmsTemplate find(Integer platform, Integer scene) {
        return smsTemplateDao.find(platform, scene);
    }
}
