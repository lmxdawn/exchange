package com.lmxdawn.other.controller;

import com.lmxdawn.other.constant.CacheConstant;
import com.lmxdawn.other.constant.CodeConstant;
import com.lmxdawn.other.entity.CodeTemplate;
import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.req.EmailSendReq;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.service.SettingService;
import com.lmxdawn.other.service.CodeTemplateService;
import com.lmxdawn.other.util.EmailUtil;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.vo.EmailSendVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码相关")
@RestController
@RequestMapping("/email")
public class EmailController {

    @Resource
    private SettingService settingService;

    @Resource
    private CodeTemplateService codeTemplateService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @ApiOperation(value = "发送邮箱", notes = "<font size=\"5\" color=\"red\">如果后台关闭验证后，这里data里面返回code码，前端默认加上去</font>")
    @PostMapping("/send")
    public BaseRes send(@RequestBody @Validated EmailSendReq req,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // 默认用自带的邮箱
        EmailSendVo vo = settingService.listToEmailSendVo();

        CodeTemplate codeTemplate = codeTemplateService.find(CodeConstant.EMAIL, req.getScene(), req.getLang());

        if (vo == null || codeTemplate == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        String tt = req.getScene() + ":" + req.getEmail();
        String key = String.format(CacheConstant.CODE_SEND, tt);
        String code = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(code)) {
            try {
                code = String.valueOf(RandomUtils.nextInt(100000, 999999));
                vo.setFrom(vo.getEmailUsername());
                vo.setTo(req.getEmail());
                vo.setSubject("");
                if (StringUtils.isBlank(codeTemplate.getTemplateParas())) {
                    vo.setText(codeTemplate.getTemplateParas().replace("NUM", code));
                }
                if (codeTemplate.getStatus() == 1) {
                    boolean is = EmailUtil.send(vo);
                    if (!is) {
                        return ResultVOUtils.error(ResultEnum.OTHER_EMAIL_ERR);
                    }
                }
                redisTemplate.opsForValue().set(key, code, 60 * 2, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultVOUtils.error(ResultEnum.OTHER_EMAIL_ERR);
            }

        }
        String res = codeTemplate.getStatus() == 1 ? "" : code;
        return ResultVOUtils.success(res);
    }

}
