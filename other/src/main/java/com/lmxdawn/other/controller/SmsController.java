package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.constant.CacheConstant;
import com.lmxdawn.other.constant.CodeConstant;
import com.lmxdawn.other.entity.CodeTemplate;
import com.lmxdawn.other.req.SmsSendReq;
import com.lmxdawn.other.res.HuaWeiSmsSendRes;
import com.lmxdawn.other.service.SettingService;
import com.lmxdawn.other.service.CodeTemplateService;
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

@Api(tags = "验证码相关")
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Resource
    private SettingService settingService;

    @Resource
    private CodeTemplateService codeTemplateService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @ApiOperation(value = "发送短信", notes = "<font size=\"5\" color=\"red\">如果后台关闭验证后，这里data里面返回code码，前端默认加上去</font>")
    @PostMapping("/send")
    public BaseRes send(@RequestBody @Validated SmsSendReq req,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // 默认用华为云短信发送，这里可以两个同时用，自己做判断，比如用华为云发送失败/超时后，继续用阿里云发送
        // 目前只实现了华为云短信
        HuaWeiSmsSendVo huaWeiSmsSendVo = settingService.listToHuaWeiSmsVo();

        CodeTemplate codeTemplate = codeTemplateService.find(CodeConstant.HUAWEI_PLATFORM_SMS, req.getScene(), req.getLang());

        if (huaWeiSmsSendVo == null || codeTemplate == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        String tt = req.getScene() + ":" + req.getTel();
        String key = String.format(CacheConstant.CODE_SEND, tt);
        String code = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(code)) {
            try {
                code = String.valueOf(RandomUtils.nextInt(100000, 999999));
                huaWeiSmsSendVo.setTemplateId(codeTemplate.getTemplateId());
                if (StringUtils.isBlank(codeTemplate.getTemplateParas())) {
                    huaWeiSmsSendVo.setTemplateParas(codeTemplate.getTemplateParas().replace("NUM", code));
                }
                huaWeiSmsSendVo.setReceiver(req.getTel());
                if (codeTemplate.getStatus() == 1) {
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
        String res = codeTemplate.getStatus() == 1 ? "" : code;
        return ResultVOUtils.success(res);
    }

}
