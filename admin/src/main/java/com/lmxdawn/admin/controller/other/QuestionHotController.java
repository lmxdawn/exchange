package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.QuestionHotQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.QuestionHotSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.QuestionHotInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.QuestionHotDubboService;
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
 * 热门问题
 */
@Api(tags = "热门问题管理")
@RestController
@Slf4j
public class QuestionHotController {

    @DubboReference
    private QuestionHotDubboService questionHotDubboService;

    @ApiOperation(value = "热门问题列表")
    @AuthRuleAnnotation(value = "other/question-hot/index")
    @GetMapping(value = "other/question-hot/index")
    public BaseRes<PageSimpleDubboRes<QuestionHotInfoDubboRes>> index(@Valid QuestionHotQueryDubboReq req,
                                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<QuestionHotInfoDubboRes> list = questionHotDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "热门问题添加")
    @AuthRuleAnnotation(value = "other/question-hot/save")
    @PostMapping(value = "other/question-hot/save")
    public BaseRes save(@RequestBody @Valid QuestionHotSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = questionHotDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "热门问题修改")
    @AuthRuleAnnotation(value = "other/question-hot/edit")
    @PostMapping(value = "other/question-hot/edit")
    public BaseRes edit(@RequestBody @Valid QuestionHotSaveDubboReq req,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = questionHotDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        return ResultVOUtils.success();
    }

}
