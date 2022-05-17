package com.lmxdawn.user.controller;

import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.util.PasswordUtils;
import com.lmxdawn.user.util.ResultVOUtils;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.req.LoginPwdReq;
import com.lmxdawn.user.res.LoginTokenRes;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "授权")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private MemberService memberService;

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "密码登录")
    @PostMapping("/byPwd")
    public BaseRes<LoginTokenRes> byPwd(@RequestBody @Valid LoginPwdReq req,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String type = req.getType();
        if (!"email".equals(type) && !"tel".equals(type)) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }
        if ("email".equals(type) && (req.getEmail() == null || "".equals(req.getEmail()))) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }
        if ("tel".equals(type) && (req.getTel() == null || "".equals(req.getTel()))) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        String tel = req.getTel();
        String email = req.getEmail();
        String pwd = req.getPassword();
        Member member;
        if ("email".equals(type)) {
            member = memberService.findByEmail(email);
        } else {
            member = memberService.findByTel(tel);
        }
        if (member == null) {
            return ResultVOUtils.error(ResultEnum.USER_LOGIN_PWD_ERR);
        }
        if (!PasswordUtils.memberPwd(pwd).equals(member.getPwd())) {
            return ResultVOUtils.error(ResultEnum.USER_LOGIN_PWD_ERR);
        }

        String token = loginService.createToken(member.getMemberId());

        LoginTokenRes loginTokenRes = new LoginTokenRes();
        loginTokenRes.setMemberId(member.getMemberId());
        loginTokenRes.setToken(token);

        return ResultVOUtils.success(loginTokenRes);
    }


}
