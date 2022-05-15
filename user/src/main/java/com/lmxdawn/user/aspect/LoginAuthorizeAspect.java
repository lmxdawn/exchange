package com.lmxdawn.user.aspect;

import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.exception.JsonException;
import com.lmxdawn.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 登录验证 AOP
 */
@Aspect
@Component
@Slf4j
public class LoginAuthorizeAspect {

    @Autowired
    private LoginService loginService;

    @Pointcut("@annotation(com.lmxdawn.user.annotation.LoginAuthAnnotation)")
    public void loginVerify() {
    }

    /**
     * 登录验证
     *
     * @param joinPoint
     */
    @Before("loginVerify()")
    public void doLoginAuthVerify(JoinPoint joinPoint) {
        // 判断是否进行需要登录
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从切面中获取当前方法
        Method method = signature.getMethod();
        //得到了方,提取出他的注解
        LoginAuthAnnotation action = method.getAnnotation(LoginAuthAnnotation.class);
        boolean login = action.isLogin();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new JsonException(ResultEnum.NOT_NETWORK);
        }
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("x-token");
        if (token == null) {
            if (!login) {
                return;
            }
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        }

        // 验证 token
        Long memberId = loginService.verify(token);
        if (memberId == null || memberId <= 0) {
            if (!login) {
                return;
            }
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        }

        // 设置
        request.setAttribute("memberId", memberId);
    }

}
