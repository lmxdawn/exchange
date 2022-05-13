package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.req.AreaCodeListPageReq;
import com.lmxdawn.other.res.AreaCodeInfoRes;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.service.AreaCodeService;
import com.lmxdawn.other.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "短信相关")
@RestController
@RequestMapping("/area-code")
public class AreaCodeController {

    @Resource
    private AreaCodeService areaCodeService;

    @ApiOperation(value = "地区列表")
    @GetMapping("/list")
    public BaseRes<List<AreaCodeInfoRes>> list(@Valid AreaCodeListPageReq req,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<AreaCodeInfoRes> questionHotListInfoRes = areaCodeService.listByLang(req);

        return ResultVOUtils.success(questionHotListInfoRes);
    }

}
