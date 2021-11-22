package com.lmxdawn.admin.controller.auth;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.entity.auth.AuthAdmin;
import com.lmxdawn.admin.res.auth.LoginRes;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.exception.JsonException;
import com.lmxdawn.admin.req.auth.LoginReq;
import com.lmxdawn.admin.req.auth.UpdatePasswordReq;
import com.lmxdawn.admin.service.auth.AuthAdminService;
import com.lmxdawn.admin.service.auth.AuthLoginService;
import com.lmxdawn.admin.util.PasswordUtils;
import com.lmxdawn.admin.res.auth.LoginUserInfoRes;
import com.lmxdawn.admin.util.IpUtils;
import com.lmxdawn.admin.util.JwtUtils;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.admin.res.BaseRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * 登录相关
 */
@Api(tags = "授权")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private AuthLoginService authLoginService;

    @Autowired
    private AuthAdminService authAdminService;

    /**
     * 用户登录
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/auth/login/index")
    public BaseRes<LoginRes> index(@RequestBody @Valid LoginReq loginReq,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        AuthAdmin authAdmin = authAdminService.findByUserName(loginReq.getUserName());
        if (authAdmin == null) {
            throw new JsonException(ResultEnum.DATA_NOT, "用户名或密码错误");
        }

        if (!PasswordUtils.authAdminPwd(loginReq.getPwd()).equals(authAdmin.getPassword())) {
            throw new JsonException(ResultEnum.DATA_NOT, "用户名或密码错误");
        }

        // 更新登录状态
        AuthAdmin authAdminUp = new AuthAdmin();
        authAdminUp.setId(authAdmin.getId());
        authAdminUp.setLastLoginTime(new Date());
        authAdminUp.setLastLoginIp(IpUtils.getIpAddr(request));
        authAdminService.updateAuthAdmin(authAdminUp);

        // 登录成功后获取权限，这里面会设置到缓存
        authLoginService.listRuleByAdminId(authAdmin.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("admin_id", authAdmin.getId());
        String token = JwtUtils.createToken(claims, 86400L); // 一天后过期

        LoginRes loginRes = new LoginRes();
        loginRes.setId(authAdmin.getId());
        loginRes.setToken(token);

        return ResultVOUtils.success(loginRes);
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @AuthRuleAnnotation("")
    @GetMapping("/auth/login/userInfo")
    public BaseRes<LoginUserInfoRes> userInfo(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("admin_id");

        AuthAdmin authAdmin = authAdminService.findById(id);

        List<String> authRules = authLoginService.listRuleByAdminId(authAdmin.getId());

        LoginUserInfoRes loginUserInfoRes = new LoginUserInfoRes();
        BeanUtils.copyProperties(authAdmin, loginUserInfoRes);
        loginUserInfoRes.setAuthRules(authRules);

        return ResultVOUtils.success(loginUserInfoRes);
    }

    /**
     * 登出
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/auth/login/out")
    public BaseRes out(){
        return ResultVOUtils.success();
    }

    /**
     * 修改密码
     * @return
     */
    @ApiOperation(value = "修改密码")
    @AuthRuleAnnotation("") // 需要登录验证,但是不需要权限验证时,value 值填空字符串
    @PostMapping("/auth/login/password")
    public BaseRes password(@RequestBody @Valid UpdatePasswordReq updatePasswordReq,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        AuthAdmin authAdmin = authAdminService.findPwdById(updatePasswordReq.getAdminId());
        if (authAdmin == null) {
            throw new JsonException(ResultEnum.DATA_NOT);
        }
        String oldPwd = PasswordUtils.authAdminPwd(updatePasswordReq.getOldPassword());
        // 旧密码不对
        if (authAdmin.getPassword() != null
                && !authAdmin.getPassword().equals(oldPwd)) {
            throw new JsonException(ResultEnum.DATA_NOT, "旧密码匹配失败");
        }

        AuthAdmin authAdminUp = new AuthAdmin();
        authAdminUp.setId(authAdmin.getId());
        String newPwd = PasswordUtils.authAdminPwd(updatePasswordReq.getNewPassword());
        authAdminUp.setPassword(newPwd);

        boolean b = authAdminService.updateAuthAdmin(authAdminUp);
        if (b) {
            return ResultVOUtils.success();
        }

        return ResultVOUtils.error(ResultEnum.DATA_CHANGE);
    }

}
