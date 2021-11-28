package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.req.DeleteIdReq;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.SmsTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SmsTemplateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SmsTemplateInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.SmsTemplateDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关
 */
@Api(tags = "其它管理")
@RestController
@Slf4j
public class SmsTemplateController {

    @DubboReference
    private SmsTemplateDubboService smsTemplateDubboService;

    @ApiOperation(value = "短信模板列表")
    @AuthRuleAnnotation(value = "other/sms-template/index")
    @GetMapping(value = "other/sms-template/index")
    public BaseRes<PageSimpleDubboRes<SmsTemplateInfoDubboRes>> index(@Valid SmsTemplateQueryDubboReq smsTemplateQueryDubboReq,
                                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<SmsTemplateInfoDubboRes> list = smsTemplateDubboService.list(smsTemplateQueryDubboReq);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "短信模板添加")
    @AuthRuleAnnotation(value = "other/sms-template/save")
    @PostMapping(value = "other/sms-template/save")
    public BaseRes save(@RequestBody @Valid SmsTemplateSaveDubboReq smsTemplateSaveDubboReq,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = smsTemplateDubboService.insert(smsTemplateSaveDubboReq);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前平台下的场景已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "短信模板修改")
    @AuthRuleAnnotation(value = "other/sms-template/edit")
    @PostMapping(value = "other/sms-template/edit")
    public BaseRes edit(@RequestBody @Valid SmsTemplateSaveDubboReq smsTemplateSaveDubboReq,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (smsTemplateSaveDubboReq.getId() == null || smsTemplateSaveDubboReq.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = smsTemplateDubboService.update(smsTemplateSaveDubboReq);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前平台下的场景已存在");
        }

        return ResultVOUtils.success();
    }


    @ApiOperation(value = "短信模板删除")
    @AuthRuleAnnotation(value = "other/sms-template/delete")
    @PostMapping(value = "other/sms-template/delete")
    public BaseRes delete(@RequestBody @Valid DeleteIdReq deleteIdReq,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        boolean delete = smsTemplateDubboService.delete(deleteIdReq.getId());

        return ResultVOUtils.success();
    }

}
