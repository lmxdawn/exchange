package com.lmxdawn.other.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.constant.CacheConstant;
import com.lmxdawn.other.constant.SmsConstant;
import com.lmxdawn.other.constant.SmsCountryCodeConstant;
import com.lmxdawn.other.entity.SmsTemplate;
import com.lmxdawn.other.req.SmsSendReq;
import com.lmxdawn.other.res.HuaWeiSmsSendRes;
import com.lmxdawn.other.service.SettingService;
import com.lmxdawn.other.service.SmsTemplateService;
import com.lmxdawn.other.util.HuaWeiSmsUtil;
import com.lmxdawn.other.vo.HuaWeiSmsSendVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Api(tags = "短信相关")
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Resource
    private SettingService settingService;

    @Resource
    private SmsTemplateService smsTemplateService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "短信的区号列表")
    @GetMapping("/countryCode")
    public BaseRes countryCode() {

        System.out.println(redisTemplate.opsForValue().get("*"));

        JSONArray objects = JSON.parseArray(SmsCountryCodeConstant.LIST_JSON);

        return ResultVOUtils.success(objects.toArray());
    }


    @ApiOperation(value = "发送短信", notes = "<font size=\"5\" color=\"red\">如果后台关闭验证后，这里data里面返回code码，前端默认加上去</font>")
    @PostMapping("/send")
    public BaseRes send(@RequestBody @Validated SmsSendReq smsSendReq,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // 默认用华为云短信发送，这里可以两个同时用，自己做判断，比如用华为云发送失败/超时后，继续用阿里云发送
        // 目前只实现了华为云短信
        HuaWeiSmsSendVo huaWeiSmsSendVo = settingService.listToHuaWeiSmsVo();

        SmsTemplate smsTemplate = smsTemplateService.find(SmsConstant.HUAWEI_PLATFORM, smsSendReq.getScene());

        if (huaWeiSmsSendVo == null || smsTemplate == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        String tt = smsSendReq.getScene() + ":" + smsSendReq.getTel();
        String key = String.format(CacheConstant.SMS_SEND, tt);
        String code = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(code)) {
            try {
                code = String.valueOf(RandomUtils.nextInt(100000, 999999));
                huaWeiSmsSendVo.setTemplateId(smsTemplate.getTemplateId());
                if (StringUtils.isBlank(smsTemplate.getTemplateParas())) {
                    huaWeiSmsSendVo.setTemplateParas(smsTemplate.getTemplateParas().replace("NUM", code));
                }
                huaWeiSmsSendVo.setReceiver(smsSendReq.getTel());
                if (smsTemplate.getStatus() == 1) {
                    HuaWeiSmsSendRes huaWeiSmsSendRes = HuaWeiSmsUtil.send(huaWeiSmsSendVo);
                    if (!"000000".equals(huaWeiSmsSendRes.getCode())) {
                        return ResultVOUtils.error(ResultEnum.OTHER_SMS_ERR, huaWeiSmsSendRes.getDescription());
                    }
                }
                redisTemplate.opsForValue().set(key, code, 60 * 2, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultVOUtils.error(ResultEnum.OTHER_SMS_ERR);
            }

        }
        String res = smsTemplate.getStatus() == 1 ? "" : code;
        return ResultVOUtils.success(res);
    }

}
