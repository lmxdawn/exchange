package com.lmxdawn.user.controller;

import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.req.MemberBillListPageReq;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.res.MemberBillRes;
import com.lmxdawn.user.service.MemberBillService;
import com.lmxdawn.user.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户钱包")
@RestController
@RequestMapping("/member-bill")
public class MemberBillController {

    @Autowired
    private MemberBillService memberBillService;

    @ApiOperation(value = "获取用户账单")
    @GetMapping("list")
    @LoginAuthAnnotation
    public BaseRes<MemberBillRes> list(@Valid MemberBillListPageReq req,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");
        req.setMemberId(memberId);
        List<MemberBillRes> list = memberBillService.listPage(req);

        return ResultVOUtils.success(list);
    }

}
