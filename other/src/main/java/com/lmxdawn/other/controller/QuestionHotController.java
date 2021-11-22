package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.req.QuestionHotListPageReq;
import com.lmxdawn.other.res.QuestionHotListInfoRes;
import com.lmxdawn.other.service.QuestionHotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "帮助中心")
@RestController
@RequestMapping("/help")
public class QuestionHotController {

    @Resource
    private QuestionHotService questionHotService;

    @ApiOperation(value = "热门问题列表")
    @GetMapping("/question-hot/list")
    public BaseRes<List<QuestionHotListInfoRes>> list(@Valid QuestionHotListPageReq req,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<QuestionHotListInfoRes> questionHotListInfoRes = questionHotService.listPage(req);

        return ResultVOUtils.success(questionHotListInfoRes);
    }

}
