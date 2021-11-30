package com.lmxdawn.user.controller;

import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.res.MemberCoinRes;
import com.lmxdawn.user.service.MemberCoinService;
import com.lmxdawn.user.util.ResultVOUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户钱包")
@RestController
@RequestMapping("/member-coin")
public class MemberCoinController {

    @Autowired
    private MemberCoinService memberCoinService;

    @ApiOperation(value = "获取用户钱包列表")
    @GetMapping("list")
    @LoginAuthAnnotation
    public BaseRes<MemberCoinRes> list(HttpServletRequest request) {

        Long memberId = (Long) request.getAttribute("memberId");

        List<MemberCoinRes> list = memberCoinService.listAllByMemberId(memberId);

        return ResultVOUtils.success(list);
    }

}
