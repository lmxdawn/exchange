package com.lmxdawn.user.controller;

import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.res.MemberLoginInfoRes;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("loginInfo")
    @LoginAuthAnnotation
    public BaseRes<MemberLoginInfoRes> loginInfo(HttpServletRequest request) {

        Long memberId = (Long) request.getAttribute("memberId");

        Member member = memberService.findByMemberId(memberId);

        if (member == null) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        MemberLoginInfoRes res = new MemberLoginInfoRes();

        BeanUtils.copyProperties(member, res);

        return ResultVOUtils.success(res);
    }

}
