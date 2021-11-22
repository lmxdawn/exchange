package com.lmxdawn.user.controller;

import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.util.PasswordUtils;
import com.lmxdawn.user.util.ResultVOUtils;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.req.RegisterReq;
import com.lmxdawn.user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户注册")
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private MemberService memberService;

    @ApiOperation(value = "手机号注册")
    @PostMapping("/byTel")
    public BaseRes registerByTel(
            @RequestBody @Validated RegisterReq registerReq,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // TODO：验证手机验证码

        String tel = registerReq.getTel();
        Member byTel = memberService.findByTel(tel);
        if (byTel != null) {
            return ResultVOUtils.error(ResultEnum.USER_REGISTER_TEL_EXISTS);
        }

        Member member = new Member();
        member.setTel(tel);
        member.setName(registerReq.getName());
        member.setPwd(PasswordUtils.memberPwd(registerReq.getPwd()));

        memberService.insertMember(member);

        return ResultVOUtils.success();
    }

}
