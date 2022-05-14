package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.req.DeleteIdReq;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.CodeTemplateQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.CodeTemplateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.CodeTemplateInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.CodeTemplateDubboService;
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
 * 其它管理
 */
@Api(tags = "其它管理")
@RestController
@Slf4j
public class CodeTemplateController {

    @DubboReference
    private CodeTemplateDubboService codeTemplateDubboService;

    @ApiOperation(value = "验证码模板列表")
    @AuthRuleAnnotation(value = "other/code-template/index")
    @GetMapping(value = "other/code-template/index")
    public BaseRes<PageSimpleDubboRes<CodeTemplateInfoDubboRes>> index(@Valid CodeTemplateQueryDubboReq codeTemplateQueryDubboReq,
                                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<CodeTemplateInfoDubboRes> list = codeTemplateDubboService.list(codeTemplateQueryDubboReq);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "验证码模板添加")
    @AuthRuleAnnotation(value = "other/code-template/save")
    @PostMapping(value = "other/code-template/save")
    public BaseRes save(@RequestBody @Valid CodeTemplateSaveDubboReq codeTemplateSaveDubboReq,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = codeTemplateDubboService.insert(codeTemplateSaveDubboReq);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前平台下的场景已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "验证码模板修改")
    @AuthRuleAnnotation(value = "other/code-template/edit")
    @PostMapping(value = "other/code-template/edit")
    public BaseRes edit(@RequestBody @Valid CodeTemplateSaveDubboReq codeTemplateSaveDubboReq,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (codeTemplateSaveDubboReq.getId() == null || codeTemplateSaveDubboReq.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = codeTemplateDubboService.update(codeTemplateSaveDubboReq);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前平台下的场景已存在");
        }

        return ResultVOUtils.success();
    }


    @ApiOperation(value = "验证码模板删除")
    @AuthRuleAnnotation(value = "other/code-template/delete")
    @PostMapping(value = "other/code-template/delete")
    public BaseRes delete(@RequestBody @Valid DeleteIdReq deleteIdReq,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        boolean delete = codeTemplateDubboService.delete(deleteIdReq.getId());

        return ResultVOUtils.success();
    }

}
