package com.lmxdawn.user.controller;

import com.lmxdawn.dubboapi.service.other.CodeDubboService;
import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.constant.CodeSceneConstant;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.req.ReviseLoginPwdReq;
import com.lmxdawn.user.req.RevisePayPwdReq;
import com.lmxdawn.user.req.SetPayPwdReq;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.res.MemberLoginInfoRes;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.util.PasswordUtils;
import com.lmxdawn.user.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "用户")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @DubboReference
    private CodeDubboService codeDubboService;

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

        Integer isPwd = member.getPwd() != null && !"".equals(member.getPwd()) ? 1 : 0;
        res.setIsPwd(isPwd);
        Integer isPayPwd = member.getPayPwd() != null && !"".equals(member.getPayPwd()) ? 1 : 0;
        res.setIsPayPwd(isPayPwd);
        Integer isGoogleKey = member.getGoogleKey() != null && !"".equals(member.getGoogleKey()) ? 1 : 0;
        res.setIsGoogleKey(isGoogleKey);

        return ResultVOUtils.success(res);
    }

    @ApiOperation(value = "修改登录密码")
    @PostMapping("reviseLoginPwd")
    @LoginAuthAnnotation
    public BaseRes reviseLoginPwd(@RequestBody @Valid ReviseLoginPwdReq req,
                                  BindingResult bindingResult,
                                  HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        Member member = memberService.findPwdByMemberId(memberId);

        if (member == null) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        String pwd = req.getPassword();
        if (!PasswordUtils.memberPwd(pwd).equals(member.getPwd())) {
            return ResultVOUtils.error(ResultEnum.USER_LOGIN_PWD_ERR);
        }

        Member memberUpdate = new Member();
        memberUpdate.setMemberId(memberId);
        memberUpdate.setPwd(PasswordUtils.memberPwd(req.getNewPassword()));
        boolean update = memberService.update(memberUpdate);
        if (!update) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    @ApiOperation(value = "修改交易录密码")
    @PostMapping("revisePayPwd")
    @LoginAuthAnnotation
    public BaseRes revisePayPwd(@RequestBody @Valid RevisePayPwdReq req,
                                BindingResult bindingResult,
                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        Member member = memberService.findPwdByMemberId(memberId);

        if (member == null) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        String pwd = req.getPassword();
        if (!PasswordUtils.memberPwd(pwd).equals(member.getPayPwd())) {
            return ResultVOUtils.error(ResultEnum.USER_LOGIN_PWD_ERR);
        }

        Member memberUpdate = new Member();
        memberUpdate.setMemberId(memberId);
        memberUpdate.setPayPwd(PasswordUtils.memberPwd(req.getNewPassword()));
        boolean update = memberService.update(memberUpdate);
        if (!update) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    @ApiOperation(value = "设置交易录密码")
    @PostMapping("setPayPwd")
    @LoginAuthAnnotation
    public BaseRes setPayPwd(@RequestBody @Valid SetPayPwdReq req,
                             BindingResult bindingResult,
                             HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        boolean verify = codeDubboService.verify(req.getTelOrEmail(), CodeSceneConstant.SET_PAY_PWD, req.getCode());
        if (!verify) {
            return ResultVOUtils.error(ResultEnum.USER_CODE_ERR);
        }

        Long memberId = (Long) request.getAttribute("memberId");

        Member memberUpdate = new Member();
        memberUpdate.setMemberId(memberId);
        memberUpdate.setPayPwd(PasswordUtils.memberPwd(req.getPassword()));
        boolean update = memberService.update(memberUpdate);
        if (!update) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }


}
